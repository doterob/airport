package es.bahiasoftware.airport.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import es.bahiasoftware.airport.model.Airport;

public interface AirportDao extends JpaRepository<Airport, String> {
}
