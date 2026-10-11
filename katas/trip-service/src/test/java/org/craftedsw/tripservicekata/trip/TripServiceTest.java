package org.craftedsw.tripservicekata.trip;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class TripServiceTest {

	private static final User GUEST = null;
	private static final Trip TO_LONDON = new Trip();
	private static final Trip TO_PARIS = new Trip();

	private final User loggedUser = new User();
	private final User anotherUser = new User();

	private MockedStatic<TripDAO> tripDaoStatic;

	@BeforeEach
	void setUp() {
		tripDaoStatic = mockStatic(TripDAO.class);
		tripDaoStatic.when(() -> TripDAO.findTripsByUser(any())).thenAnswer(call -> call.<User>getArgument(0).trips());
	}

	@AfterEach
	void tearDown() {
		tripDaoStatic.close();
	}

	@Test
	void throwsWhenNoUserIsLoggedIn() {
		assertThatThrownBy(() -> loggedInAs(GUEST).getTripsByUser(anotherUser))
				.isInstanceOf(UserNotLoggedInException.class);
	}

	@Test
	void returnsNoTripsWhenUserHasNoFriends() {
		anotherUser.addTrip(TO_LONDON);

		assertThat(loggedInAs(loggedUser).getTripsByUser(anotherUser)).isEmpty();
	}

	@Test
	void returnsNoTripsWhenUsersAreNotFriends() {
		anotherUser.addFriend(new User());
		anotherUser.addTrip(TO_LONDON);

		assertThat(loggedInAs(loggedUser).getTripsByUser(anotherUser)).isEmpty();
	}

	@Test
	void doesNotLoadTripsWhenUsersAreNotFriends() {
		anotherUser.addFriend(new User());

		loggedInAs(loggedUser).getTripsByUser(anotherUser);

		tripDaoStatic.verify(() -> TripDAO.findTripsByUser(any()), never());
	}

	@Test
	void returnsTripsOfFriend() {
		anotherUser.addFriend(new User());
		anotherUser.addFriend(loggedUser);
		anotherUser.addTrip(TO_LONDON);
		anotherUser.addTrip(TO_PARIS);

		assertThat(loggedInAs(loggedUser).getTripsByUser(anotherUser)).containsExactly(TO_LONDON, TO_PARIS);
	}

	@Test
	void returnsNoTripsWhenFriendHasNoTrips() {
		anotherUser.addFriend(loggedUser);

		assertThat(loggedInAs(loggedUser).getTripsByUser(anotherUser)).isEmpty();
	}

	@Test
	void friendshipIsCheckedOnTheRequestedUserSide() {
		loggedUser.addFriend(anotherUser);
		anotherUser.addTrip(TO_LONDON);

		assertThat(loggedInAs(loggedUser).getTripsByUser(anotherUser)).isEmpty();
	}

	private TripService loggedInAs(User user) {
		return new TripService(() -> user);
	}
}
