package app.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.neo4j.graphdb.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.model.Route;

// TODO: Auto-generated Javadoc
/**
 * The Interface RouteService.
 */
//@Validated
@Service("routeService")
public interface RouteService {

	
	/**
	 * Gets the.
	 *
	 * @param id the id
	 * @return the route
	 */
	Route find(@Min(0) long id);
	
	Route find(@Min(0) long id, boolean lazy);
	
	/**
	 * Gets the all.
	 *
	 * @param limit the limit
	 * @param skip the skip
	 * @return the all
	 */
	@NotNull
	List<Route> findAll(@Min(1) int limit, @Min(0) int skip, boolean lazy);

	/**
	 * Gets the all by airport.
	 *
	 * @param airportId the airport id
	 * @param direction the direction
	 * @param limit the limit
	 * @param skip the skip
	 * @param lazy the lazy
	 * @return the all by airport
	 */
	@NotNull
	List<Route> findAllByAirport(@Min(0) long airportId,
			@NotNull Direction direction, @Min(1) int limit, @Min(0) int skip,
			boolean lazy);

	/**
	 * Gets the all by airline.
	 *
	 * @param airlineId the airline id
	 * @param limit the limit
	 * @param skip the skip
	 * @param lazy the lazy
	 * @return the all by airline
	 */
	@NotNull
	List<Route> findAllByAirline(@NotNull long airlineId, int limit,
			int skip, boolean lazy);
	
	
	/**
	 * Save.
	 *
	 * @param route the route
	 * @return the route
	 */
	@Transactional
	Route save(Route route);

	/**
	 * Removes the.
	 *
	 * @param id the id
	 */
	@Transactional
	void remove(@Min(0) long id);

	/**
	 * Removes the all.
	 *
	 * @param idList the id list
	 */
	@Transactional
	void removeAll(@NotNull List<Long> idList);
	
	boolean exists(Long id);
	
	long count();
		
}
