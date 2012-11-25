package app.model;

import java.util.Locale;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.index.IndexType;

// TODO: Auto-generated Javadoc
/**
 * The Class Airport.
 */
@NodeEntity
public class Airport {

	/** The id. */
	@GraphId
	private Long id;

	/** The name. */
	@NotEmpty
	private String name;

	/** The city. */
	private String city;

	/** The country. */
	@NotEmpty
	private String country;
	
	private String countryCode;
	
	
	/** The iata. */
	@Size(max = 4)
	private String iata;

	/** The icao. */
	@Size(max = 4)
	private String icao;

	/** The altitude. */
	@Min(0) @Nullable
	private Double altitude;

	/** The timezone. */
	private double timezone;

	/** The latitude. */
	private double latitude;

	/** The longitude. */
	private double longitude;
	
	
	/** The wkt. */
	@Indexed(indexType = IndexType.POINT, indexName = "AirportLocation")
	private String wkt;

	/**
	 * Sets the location.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void setLocation(final double x, final double y) {
		Locale enLocale = new Locale("en", "EN");
		this.wkt = String.format(enLocale, "POINT( %.2f %.2f )", x, y);
		this.longitude = x;
		this.latitude = y;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(final Long id) {
		this.id = id;
	}


	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(final String city) {
		this.city = city;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(final String country) {
		this.country = country;
	}
	
	
	
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * Gets the iata.
	 *
	 * @return the iata
	 */
	public String getIata() {
		return iata;
	}

	/**
	 * Sets the iata.
	 *
	 * @param iata the new iata
	 */
	public void setIata(final String iata) {
		this.iata = iata;
	}

	/**
	 * Gets the icao.
	 *
	 * @return the icao
	 */
	public String getIcao() {
		return icao;
	}

	/**
	 * Sets the icao.
	 *
	 * @param icao the new icao
	 */
	public void setIcao(final String icao) {
		this.icao = icao;
	}

	/**
	 * Gets the altitude.
	 *
	 * @return the altitude
	 */
	public Double getAltitude() {
		return altitude;
	}

	/**
	 * Sets the altitude.
	 *
	 * @param altitude the new altitude
	 */
	public void setAltitude(final Double altitude) {
		this.altitude = altitude;
	}

	/**
	 * Gets the timezone.
	 *
	 * @return the timezone
	 */
	public double getTimezone() {
		return timezone;
	}

	/**
	 * Sets the timezone.
	 *
	 * @param timezone the new timezone
	 */
	public void setTimezone(final double timezone) {
		this.timezone = timezone;
	}

	/**
	 * Gets the latitude.
	 *
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude.
	 *
	 * @param latitude the new latitude
	 */
	public void setLatitude(final double latitude) {
		this.latitude = latitude;
		setLocation(longitude, latitude);
	}

	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude.
	 *
	 * @param longitude the new longitude
	 */
	public void setLongitude(final double longitude) {
		this.longitude = longitude;
		setLocation(longitude, latitude);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Airport [id=" + id + ", name=" + name + ", city=" + city + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Airport other = (Airport) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
