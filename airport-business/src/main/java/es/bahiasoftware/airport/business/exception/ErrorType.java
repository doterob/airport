package es.bahiasoftware.airport.business.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {
	
	AIRCRAFT_NOT_EXISTS(HttpStatus.NOT_FOUND),
	AIRCRAFT_RETIRED(HttpStatus.CONFLICT),
	OTHER_CAUSE(HttpStatus.INTERNAL_SERVER_ERROR);
	
	private final HttpStatus status;

	private ErrorType(HttpStatus status) {
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}
}
