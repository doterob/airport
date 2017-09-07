package es.bahiasoftware.airport.web.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.bahiasoftware.airport.business.exception.AppException;
import es.bahiasoftware.airport.business.manager.FleetManager;
import es.bahiasoftware.airport.business.service.AircraftService;
import es.bahiasoftware.airport.model.Aircraft;

@RestController
@RequestMapping("/aircraft")
public class AircraftEndpoint {
	
	@Autowired
	private AircraftService service;
	
	@Autowired
	private FleetManager manager;

	@GetMapping("/ping")
	public String ping() {
		return "null";
	}

	@PostMapping("/find")
	public List<Aircraft> search() throws AppException {
		return service.find();
	}

	@GetMapping("/{id}")
	public Aircraft get(@PathVariable String id) throws AppException {
		return service.get(id);
	}

	@PostMapping
	public ResponseEntity<Aircraft> add(@RequestBody Aircraft plane) throws AppException {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.upsert(plane));
	}
	
	@PostMapping("/retire/{id}")
	public void retire(@PathVariable String id) throws AppException {
		 manager.retire(id);
	}

}
