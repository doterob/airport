package es.bahiasoftware.airport.business.manager;

import es.bahiasoftware.airport.business.exception.AppException;
import es.bahiasoftware.airport.model.Aircraft;

public interface FleetManager {

	Aircraft retire(String id) throws AppException;
}
