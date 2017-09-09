package es.bahiasoftware.airport.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import es.bahiasoftware.airport.business.exception.AppException;
import es.bahiasoftware.airport.business.exception.ErrorType;
import es.bahiasoftware.airport.model.Aircraft;
import es.bahiasoftware.airport.persistence.AircraftDao;

@Service
public class AircraftServiceImpl implements AircraftService {
	
	@Autowired
	private AircraftDao repository;
	
	@Autowired
	private AircraftImporter importer;
	
	@Transactional(rollbackFor=AppException.class)
	@Override
	public final Aircraft upsert(Aircraft aircraft) throws AppException {
		return repository.save(aircraft);
	}

	@Override
	public Aircraft get(String id) throws AppException {
		return repository.findOne(id);
	}

	@Override
	public List<Aircraft> find() throws AppException {
		return repository.findAll();
	}
	
	public Aircraft discover(String manufacturer, String model) throws AppException {
		Aircraft result = repository.findByManufacturerAndModelAllIgnoreCase(manufacturer, model);
		
		if(result == null) {
			result = importer.find(manufacturer, model);
		}
		
		return result != null ? upsert(result) : null;
	}

}
