package org.craftedsw.tripservicekata.trip;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.craftedsw.tripservicekata.exception.CollaboratorCallException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Test;

class TripDAOTest {

	@Test
	void cannotBeCalledFromUnitTests() {
		assertThatThrownBy(() -> TripDAO.findTripsByUser(new User()))
				.isInstanceOf(CollaboratorCallException.class);
	}
}
