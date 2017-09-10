package es.bahiasoftware.airport.web.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.bahiasoftware.airport.business.exception.AppException;
import es.bahiasoftware.airport.business.manager.airport.AirportService;
import es.bahiasoftware.airport.model.Airport;

@RestController
@RequestMapping("/airport")
public class AirportEndpoint {
	
	@Autowired
	private AirportService service;
	
	@GetMapping("/{id}")
	public Airport get(@PathVariable String id) throws Exception {
		return service.get(id);
	}

	@GetMapping("/find")
	public List<Airport> find() throws Exception {
		return service.findAll();
	}

	

}
