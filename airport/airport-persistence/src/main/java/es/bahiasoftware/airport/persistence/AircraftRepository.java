package es.bahiasoftware.airport.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import es.bahiasoftware.airport.model.Aircraft;

public interface AircraftRepository extends JpaRepository<Aircraft, String> {
}
