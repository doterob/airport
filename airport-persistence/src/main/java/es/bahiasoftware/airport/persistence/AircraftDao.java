package es.bahiasoftware.airport.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import es.bahiasoftware.airport.model.Aircraft;

public interface AircraftDao extends JpaRepository<Aircraft, String> {
}
