package es.bahiasoftware.airport.web.endpoint;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.bahiasoftware.airport.business.exception.AppException;
import es.bahiasoftware.airport.business.manager.FleetManager;
import es.bahiasoftware.airport.business.service.aircraft.AircraftService;
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

	@GetMapping("/all")
	public List<Aircraft> all() throws AppException {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Aircraft> get(@PathVariable String id) throws Exception {
		return Optional
				.ofNullable(service.get(id))
				.map(aircraft -> ResponseEntity.ok().body(aircraft))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Aircraft> add(@RequestBody Aircraft plane) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.upsert(plane));
	}

	@PostMapping("/retire/{id}")
	public void retire(@PathVariable String id) throws AppException {
		manager.retire(id);
	}

	@GetMapping(value = "/find/{manufacturer}/{model}")
	public ResponseEntity<Aircraft> find(@PathVariable("manufacturer") String manufacturer,
			@PathVariable("model") String model) throws Exception {
		return Optional
				.ofNullable(service.discover(manufacturer, model))
				.map(aircraft -> ResponseEntity.ok().body(aircraft))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

}
