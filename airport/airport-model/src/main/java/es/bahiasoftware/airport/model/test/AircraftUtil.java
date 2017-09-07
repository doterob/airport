package es.bahiasoftware.airport.model.test;

import es.bahiasoftware.airport.model.Aircraft;
import es.bahiasoftware.airport.model.AircraftStatus;

public class AircraftUtil {

	public static Aircraft aircraft(String id) {
		final Aircraft result = new Aircraft();
		result.setId(id);
		result.setManufacturer("Boeing");
		result.setModel("380");
		result.setLength(67.9F);
		result.setWidth(79.75F);
		result.setHeight(24.45F);
		result.setPower(100000);
		result.setSeating(480);

		return result;
	}
	
	public static Aircraft aircraft(String id, AircraftStatus status) {
		final Aircraft result = aircraft(id);
		result.setStatus(status);
		return result;
	}
}
