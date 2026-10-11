package org.craftedsw.tripservicekata.user;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.craftedsw.tripservicekata.exception.CollaboratorCallException;
import org.junit.jupiter.api.Test;

class SessionLoggedUserProviderTest {

	@Test
	void readsLoggedUserFromUserSession() {
		assertThatThrownBy(() -> new SessionLoggedUserProvider().loggedUser())
				.isInstanceOf(CollaboratorCallException.class);
	}
}
