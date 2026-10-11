package org.craftedsw.tripservicekata.user;

import java.util.Optional;

public class SessionLoggedUserProvider implements LoggedUserProvider {

	@Override
	public Optional<User> loggedUser() {
		return Optional.ofNullable(UserSession.getInstance().getLoggedUser());
	}

}
