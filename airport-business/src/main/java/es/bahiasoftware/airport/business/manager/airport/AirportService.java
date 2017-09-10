package es.bahiasoftware.airport.business.manager.airport;

import java.util.List;

import es.bahiasoftware.airport.business.exception.AppException;
import es.bahiasoftware.airport.model.Airport;

public interface AirportService {

	Airport get(String iata) throws AppException;
	
	List<Airport> findAll() throws AppException;
}
