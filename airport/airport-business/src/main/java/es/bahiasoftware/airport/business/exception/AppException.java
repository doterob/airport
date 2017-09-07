package es.bahiasoftware.airport.business.exception;

public class AppException extends Exception {

	private static final long serialVersionUID = -1718766738762883329L;
	
	private final ErrorType type;
	
	public AppException(ErrorType type) {
		super(type.toString());
		this.type = type;
	}
	
	public AppException(ErrorType type, Throwable cause) {
		super(type.toString(), cause);
		this.type = type;
	}
	
	public ErrorType getType(){
		return type;
	}
}
