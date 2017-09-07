package es.bahiasoftware.airport.web.endpoint;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.bahiasoftware.airport.model.Airport;

@RestController
@RequestMapping("/airport")
public class AirportEndpoint {
	
	@GetMapping("/{id}")
	public AirportDetail get(@PathVariable String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@PostMapping("/find")
	public List<Airport> find() {
		// TODO Auto-generated method stub
		return null;
	}

	@PostMapping
	public Airport add(@RequestBody Airport airport) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
