package org.craftedsw.tripservicekata.trip;

import java.util.List;

import org.craftedsw.tripservicekata.user.User;

public class TripDAORepository implements TripRepository {

	@Override
	public List<Trip> tripsBy(User user) {
		return TripDAO.findTripsByUser(user);
	}

}
