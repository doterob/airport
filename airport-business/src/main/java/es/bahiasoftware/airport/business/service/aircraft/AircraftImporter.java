package es.bahiasoftware.airport.business.service.aircraft;

import es.bahiasoftware.airport.model.Aircraft;

public interface AircraftImporter {

	Aircraft find(String manufacturer, String model);
}
