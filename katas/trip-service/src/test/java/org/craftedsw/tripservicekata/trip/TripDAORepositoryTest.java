package org.craftedsw.tripservicekata.trip;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.craftedsw.tripservicekata.exception.CollaboratorCallException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Test;

class TripDAORepositoryTest {

	@Test
	void loadsTripsThroughTripDAO() {
		assertThatThrownBy(() -> new TripDAORepository().tripsBy(new User()))
				.isInstanceOf(CollaboratorCallException.class);
	}
}
