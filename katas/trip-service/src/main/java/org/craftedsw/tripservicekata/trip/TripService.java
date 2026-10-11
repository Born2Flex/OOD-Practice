package org.craftedsw.tripservicekata.trip;

import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.LoggedUserProvider;
import org.craftedsw.tripservicekata.user.User;

public class TripService {

	private final LoggedUserProvider loggedUserProvider;
	private final TripRepository tripRepository;

	public TripService(LoggedUserProvider loggedUserProvider, TripRepository tripRepository) {
		this.loggedUserProvider = loggedUserProvider;
		this.tripRepository = tripRepository;
	}

	public List<Trip> getTripsByUser(User user) {
		User loggedUser = loggedUserProvider.loggedUser();
		if (loggedUser == null) {
			throw new UserNotLoggedInException();
		}
		return user.isFriendsWith(loggedUser)
				? tripRepository.tripsBy(user)
				: List.of();
	}
	
}
