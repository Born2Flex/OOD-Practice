package org.craftedsw.tripservicekata.user;

import java.util.Optional;

public interface LoggedUserProvider {

	Optional<User> loggedUser();

}
