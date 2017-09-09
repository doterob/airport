package es.bahiasoftware.airport.business.service;

import java.util.List;

import es.bahiasoftware.airport.business.exception.AppException;
import es.bahiasoftware.airport.model.Aircraft;

public interface AircraftService {

	Aircraft upsert(Aircraft aircraft) throws AppException;
	
	Aircraft get(String id) throws AppException;
	
	List<Aircraft> find() throws AppException;
	
	Aircraft discover(String manufacturer, String model) throws AppException;
}
