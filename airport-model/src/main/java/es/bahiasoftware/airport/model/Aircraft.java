package es.bahiasoftware.airport.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Aircraft extends AbstractEntity {

	private static final long serialVersionUID = -7843094141157398615L;

	@Id
	private String id;
	private String manufacturer;
	private String model;
	private float length;
	private float width;
	private float height;
	private int power;
	private int seating;
	private AircraftStatus status;
	
	public Aircraft() {
		this.status = AircraftStatus.AVAILABLE;
	}
	
	public Aircraft(String id) {
		this.id = id;
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getSeating() {
		return seating;
	}

	public void setSeating(int seating) {
		this.seating = seating;
	}

	public AircraftStatus getStatus() {
		return status;
	}

	public void setStatus(AircraftStatus status) {
		this.status = status;
	}
}
