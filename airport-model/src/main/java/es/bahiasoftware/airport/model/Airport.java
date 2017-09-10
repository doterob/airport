package es.bahiasoftware.airport.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Airport {
	
	/** Radio de la tierra en KM */
    private static final double R = 6372.8;

    @Id
	private String id;
	private float latitude;
	private float longitude;
	private String city;
	private String country;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}	
	
	/**
     * Calcula la distancia en KM entre 2 aeropuertos
     *
     * @param airport Aeropuerto con que el que se quiere calcular la distancia
     * @return Distancia calculada en KM
     */
    public double calculateDistance(Airport airport) {
    	
    	if(airport == null) {
    		return 0;
    	}
    	
        double deltaLat = Math.toRadians(airport.latitude - this.latitude);
        double deltaLon = Math.toRadians(airport.longitude - this.longitude);
        double a =  Math.pow(Math.sin(deltaLat / 2), 2) + Math.pow(Math.sin(deltaLon / 2), 2)
                * Math.cos(this.latitude) * Math.cos(airport.latitude);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
	
	
}
