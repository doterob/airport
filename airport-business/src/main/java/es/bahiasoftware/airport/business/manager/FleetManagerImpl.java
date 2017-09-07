package es.bahiasoftware.airport.business.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.bahiasoftware.airport.business.exception.AppException;
import es.bahiasoftware.airport.business.exception.ErrorType;
import es.bahiasoftware.airport.business.service.AircraftService;
import es.bahiasoftware.airport.model.Aircraft;
import es.bahiasoftware.airport.model.AircraftStatus;

@Service
public class FleetManagerImpl implements FleetManager {
	
	@Autowired
	private AircraftService aircraftService;

	@Override
	public Aircraft retire(String id) throws AppException {

		final Aircraft aircraft = aircraftService.get(id);
		
		if(AircraftStatus.RETIRED.equals(aircraft.getStatus())) {
			throw new AppException(ErrorType.AIRCRAFT_RETIRED);
		}
		
		aircraft.setStatus(AircraftStatus.RETIRED);
		aircraftService.upsert(aircraft);
		
		return aircraft;
		
	}
}
