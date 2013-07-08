package app.model;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class PathBean.
 */
public class PathBean {
	
	/** The start. */
	private Airport start;
	
	/** The end. */
	private Airport end;
	
	/** The routes. */
	private List<Route> routes;
	
	/** The length. */
	private Integer length;
	
	/** The price. */
	private Integer price;
	
	/** The distance. */
	private Integer distance;
	
	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public Airport getStart() {
		return start;
	}

	/**
	 * Sets the start.
	 *
	 * @param start the new start
	 */
	public void setStart(Airport start) {
		this.start = start;
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	public Airport getEnd() {
		return end;
	}

	/**
	 * Sets the end.
	 *
	 * @param end the new end
	 */
	public void setEnd(Airport end) {
		this.end = end;
	}

	/**
	 * Gets the routes.
	 *
	 * @return the routes
	 */
	public List<Route> getRoutes() {
		return routes;
	}

	/**
	 * Sets the routes.
	 *
	 * @param routes the new routes
	 */
	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public Integer getLength() {
		return length;
	}

	/**
	 * Sets the length.
	 *
	 * @param length the new length
	 */
	public void setLength(Integer length) {
		this.length = length;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public Integer getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(Integer price) {
		this.price = price;
	}

	/**
	 * Gets the distance.
	 *
	 * @return the distance
	 */
	public Integer getDistance() {
		return distance;
	}

	/**
	 * Sets the distance.
	 *
	 * @param distance the new distance
	 */
	public void setDistance(final Integer distance) {
		this.distance = distance;
	}


	@Override
	public String toString() {
		return "PathBean [start=" + start + ", end=" + end + ", routes="
				+ routes + ", length=" + length + ", price=" + price
				+ ", distance=" + distance + "]";
	}
	
	
	

}
