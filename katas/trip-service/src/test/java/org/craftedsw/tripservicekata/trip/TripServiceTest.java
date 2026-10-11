package org.craftedsw.tripservicekata.trip;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Test;

class TripServiceTest {

	private static final Trip TO_LONDON = new Trip();
	private static final Trip TO_PARIS = new Trip();

	private final User loggedUser = new User();
	private final User anotherUser = new User();
	private final List<User> usersWithLoadedTrips = new ArrayList<>();

	@Test
	void throwsWhenNoUserIsLoggedIn() {
		assertThatThrownBy(() -> asGuest().getTripsByUser(anotherUser))
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
	void returnsUnmodifiableEmptyListWhenUsersAreNotFriends() {
		List<Trip> trips = loggedInAs(loggedUser).getTripsByUser(anotherUser);

		assertThatThrownBy(() -> trips.add(TO_LONDON))
				.isInstanceOf(UnsupportedOperationException.class);
	}

	@Test
	void doesNotLoadTripsWhenUsersAreNotFriends() {
		anotherUser.addFriend(new User());

		loggedInAs(loggedUser).getTripsByUser(anotherUser);

		assertThat(usersWithLoadedTrips).isEmpty();
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

	private TripService asGuest() {
		return new TripService(Optional::empty, this::loadTrips);
	}

	private TripService loggedInAs(User user) {
		return new TripService(() -> Optional.of(user), this::loadTrips);
	}

	private List<Trip> loadTrips(User user) {
		usersWithLoadedTrips.add(user);
		return user.trips();
	}
}
