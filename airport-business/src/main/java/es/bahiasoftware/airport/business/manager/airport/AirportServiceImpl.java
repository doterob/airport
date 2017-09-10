package es.bahiasoftware.airport.business.manager.airport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.bahiasoftware.airport.business.exception.AppException;
import es.bahiasoftware.airport.model.Airport;
import es.bahiasoftware.airport.persistence.AirportDao;

@Service
public class AirportServiceImpl implements AirportService {

	@Autowired
	private AirportDao repository;
	
	@Override
	public Airport get(String id) throws AppException {
		return repository.findOne(id);
	}

	@Override
	public List<Airport> findAll() throws AppException {
		return repository.findAll();
	}

}
