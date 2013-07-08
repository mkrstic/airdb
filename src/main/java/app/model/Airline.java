package app.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Airline.
 */
@NodeEntity
public class Airline {

	/** The id. */
	@GraphId
	private Long id;

	/** The name. */
	@NotEmpty
	private String name;

	/** The country. */
	private String country;

	/** The iata. */
	@Size(max = 4)
	private String iata;

	/** The icao. */
	@Size(max = 4)
	private String icao;

	/** The callsign. */
	private String callsign;

	/** The active. */
	private boolean active;

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
	 * Gets the callsign.
	 *
	 * @return the callsign
	 */
	public String getCallsign() {
		return callsign;
	}

	/**
	 * Sets the callsign.
	 *
	 * @param callsign the new callsign
	 */
	public void setCallsign(final String callsign) {
		this.callsign = callsign;
	}

	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(final boolean active) {
		this.active = active;
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
		Airline other = (Airline) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Airline [id=" + id + ", name=" + name + ", country=" + country
				+ ", iata=" + iata + ", icao=" + icao + "]";
	}

	

}
