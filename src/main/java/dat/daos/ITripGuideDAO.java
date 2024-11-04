package dat.daos;

import dat.dtos.TripDTO;

import java.util.Set;

public interface ITripGuideDAO {

    void addGuideToTrip(long tripId, long guideId);
    Set<TripDTO> getTripsByGuide(long guideId);
}
