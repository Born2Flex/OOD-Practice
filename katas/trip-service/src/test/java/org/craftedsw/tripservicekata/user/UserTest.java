package org.craftedsw.tripservicekata.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.craftedsw.tripservicekata.trip.Trip;
import org.junit.jupiter.api.Test;

class UserTest {

	private final User user = new User();

	@Test
	void hasNoFriendsByDefault() {
		assertThat(user.getFriends()).isEmpty();
	}

	@Test
	void keepsAddedFriendsInOrder() {
		User bob = new User();
		User alice = new User();

		user.addFriend(bob);
		user.addFriend(alice);

		assertThat(user.getFriends()).containsExactly(bob, alice);
	}

	@Test
	void friendshipIsOneDirectional() {
		User bob = new User();

		user.addFriend(bob);

		assertThat(bob.getFriends()).doesNotContain(user);
	}

	@Test
	void isNotFriendsWithAnyoneByDefault() {
		assertThat(user.isFriendsWith(new User())).isFalse();
	}

	@Test
	void isFriendsWithAddedFriend() {
		User bob = new User();

		user.addFriend(new User());
		user.addFriend(bob);

		assertThat(user.isFriendsWith(bob)).isTrue();
	}

	@Test
	void isNotFriendsWithUserWhoAddedThemAsFriend() {
		User bob = new User();

		bob.addFriend(user);

		assertThat(user.isFriendsWith(bob)).isFalse();
	}

	@Test
	void hasNoTripsByDefault() {
		assertThat(user.trips()).isEmpty();
	}

	@Test
	void keepsAddedTripsInOrder() {
		Trip toLondon = new Trip();
		Trip toParis = new Trip();

		user.addTrip(toLondon);
		user.addTrip(toParis);

		assertThat(user.trips()).containsExactly(toLondon, toParis);
	}
}
