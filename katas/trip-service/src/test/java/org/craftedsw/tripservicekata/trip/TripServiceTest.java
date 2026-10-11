package org.craftedsw.tripservicekata.trip;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;
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
	private final UserSession userSession = mock(UserSession.class);
	private final TripService tripService = new TripService();

	private MockedStatic<UserSession> userSessionStatic;
	private MockedStatic<TripDAO> tripDaoStatic;

	@BeforeEach
	void setUp() {
		userSessionStatic = mockStatic(UserSession.class);
		userSessionStatic.when(UserSession::getInstance).thenReturn(userSession);
		tripDaoStatic = mockStatic(TripDAO.class);
		tripDaoStatic.when(() -> TripDAO.findTripsByUser(any())).thenAnswer(call -> call.<User>getArgument(0).trips());
	}

	@AfterEach
	void tearDown() {
		tripDaoStatic.close();
		userSessionStatic.close();
	}

	@Test
	void throwsWhenNoUserIsLoggedIn() {
		loggedInAs(GUEST);

		assertThatThrownBy(() -> tripService.getTripsByUser(anotherUser))
				.isInstanceOf(UserNotLoggedInException.class);
	}

	@Test
	void returnsNoTripsWhenUserHasNoFriends() {
		loggedInAs(loggedUser);
		anotherUser.addTrip(TO_LONDON);

		assertThat(tripService.getTripsByUser(anotherUser)).isEmpty();
	}

	@Test
	void returnsNoTripsWhenUsersAreNotFriends() {
		loggedInAs(loggedUser);
		anotherUser.addFriend(new User());
		anotherUser.addTrip(TO_LONDON);

		assertThat(tripService.getTripsByUser(anotherUser)).isEmpty();
	}

	@Test
	void doesNotLoadTripsWhenUsersAreNotFriends() {
		loggedInAs(loggedUser);
		anotherUser.addFriend(new User());

		tripService.getTripsByUser(anotherUser);

		tripDaoStatic.verify(() -> TripDAO.findTripsByUser(any()), never());
	}

	@Test
	void returnsTripsOfFriend() {
		loggedInAs(loggedUser);
		anotherUser.addFriend(new User());
		anotherUser.addFriend(loggedUser);
		anotherUser.addTrip(TO_LONDON);
		anotherUser.addTrip(TO_PARIS);

		assertThat(tripService.getTripsByUser(anotherUser)).containsExactly(TO_LONDON, TO_PARIS);
	}

	@Test
	void returnsNoTripsWhenFriendHasNoTrips() {
		loggedInAs(loggedUser);
		anotherUser.addFriend(loggedUser);

		assertThat(tripService.getTripsByUser(anotherUser)).isEmpty();
	}

	@Test
	void friendshipIsCheckedOnTheRequestedUserSide() {
		loggedInAs(loggedUser);
		loggedUser.addFriend(anotherUser);
		anotherUser.addTrip(TO_LONDON);

		assertThat(tripService.getTripsByUser(anotherUser)).isEmpty();
	}

	private void loggedInAs(User user) {
		when(userSession.getLoggedUser()).thenReturn(user);
	}
}
