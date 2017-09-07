package es.bahiasoftware.airport.model;

import java.sql.Timestamp;

public class Flight {

	private Fleet fleet;
	private Timestamp date;
	private Airport from;
	private Airport to;
	private Timestamp departure;
	private Timestamp arrival;
	private FlightStatus status;
}
