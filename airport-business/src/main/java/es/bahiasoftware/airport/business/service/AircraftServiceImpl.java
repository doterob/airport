package es.bahiasoftware.airport.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.bahiasoftware.airport.business.exception.AppException;
import es.bahiasoftware.airport.business.exception.ErrorType;
import es.bahiasoftware.airport.model.Aircraft;
import es.bahiasoftware.airport.persistence.AircraftRepository;

@Service
public class AircraftServiceImpl implements AircraftService {
	
	@Autowired
	private AircraftRepository repository;
	
	@Transactional(rollbackFor=AppException.class)
	@Override
	public final Aircraft upsert(Aircraft aircraft) throws AppException {
		return repository.save(aircraft);
	}

	@Override
	public Aircraft get(String id) throws AppException {
		Aircraft result = repository.findOne(id);
		if(result == null) {
			throw new AppException(ErrorType.AIRCRAFT_NOT_EXISTS);
		}
		return result;
	}

	@Override
	public List<Aircraft> find() throws AppException {
		return repository.findAll();
	}

}
