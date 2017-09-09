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

		final Aircraft result = aircraftService.get(id);
		
		if(result == null) {
			throw new AppException(ErrorType.AIRCRAFT_NOT_EXISTS);
		}
		
		if(AircraftStatus.RETIRED.equals(result.getStatus())) {
			throw new AppException(ErrorType.AIRCRAFT_RETIRED);
		}
		
		result.setStatus(AircraftStatus.RETIRED);
		aircraftService.upsert(result);
		
		return result;
		
	}
}
