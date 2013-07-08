package app.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;


// TODO: Auto-generated Javadoc
/**
 * The Class Route.
 */
@RelationshipEntity(type = RelTypes.ROUTE)
public class Route {

	/** The id. */
	@GraphId
	private Long id;

	/** The alid. */
	@Indexed
	private Long alid;

	/** The source. */
	@NotNull
	@StartNode
	private Airport source;

	/** The dest. */
	@NotNull
	@EndNode
	private Airport dest;

	/** The equipment. */
	private String equipment;

	/** The price. */
	@Min(0)
	@Indexed(numeric = true)
	private int price;

	/** The distance. */
	@Min(1)
	@Indexed(numeric = true)
	private int distance;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public Airport getSource() {
		return source;
	}
	
	/**
	 * Gets the dest.
	 *
	 * @return the dest
	 */
	public Airport getDest() {
		return dest;
	}

	/**
	 * Gets the equipment.
	 *
	 * @return the equipment
	 */
	public String getEquipment() {
		return equipment;
	}


	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public int getPrice() {
		return price;
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
	 * Sets the source.
	 *
	 * @param source the new source
	 */
	public void setSource(final Airport source) {
		this.source = source;
	}

	/**
	 * Sets the dest.
	 *
	 * @param dest the new dest
	 */
	public void setDest(final Airport dest) {
		this.dest = dest;
	}

	/**
	 * Sets the equipment.
	 *
	 * @param equipment the new equipment
	 */
	public void setEquipment(final String equipment) {
		this.equipment = equipment;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(final int price) {
		this.price = price;
	}

	/**
	 * Gets the distance.
	 *
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * Sets the distance.
	 *
	 * @param distanceKm the new distance
	 */
	public void setDistance(final int distanceKm) {
		this.distance = distanceKm;
	}
	

	/**
	 * Gets the alid.
	 *
	 * @return the alid
	 */
	public Long getAlid() {
		return alid;
	}

	/**
	 * Sets the alid.
	 *
	 * @param alid the new alid
	 */
	public void setAlid(final Long alid) {
		this.alid = alid;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Route [id=" + id + ", alid=" + alid + ", source=" + source
				+ ", dest=" + dest + ", price=" + price + "]";
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
		Route other = (Route) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
