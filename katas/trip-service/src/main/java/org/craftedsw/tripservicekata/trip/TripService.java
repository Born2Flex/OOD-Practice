package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
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

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		List<Trip> tripList = new ArrayList<Trip>();
		User loggedUser = loggedUserProvider.loggedUser();
		if (loggedUser != null) {
			if (user.isFriendsWith(loggedUser)) {
				tripList = tripRepository.tripsBy(user);
			}
			return tripList;
		} else {
			throw new UserNotLoggedInException();
		}
	}
	
}
