package app.service;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.neo4j.graphdb.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.model.Airport;

// TODO: Auto-generated Javadoc
/**
 * The Interface AirportService.
 */
//@Validated
@Service("airportService")
public interface AirportService {

	
	/**
	 * Gets the.
	 *
	 * @param id the id
	 * @return the airport
	 */
	Airport find(@Min(0) long id);
	
	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@NotNull
	List<Airport> findAll();
	
	@NotNull
	List<Airport> findAllByCountry(long countryId, int limit, int skip);
	
	@NotNull
	long countByCountry(long countryId);
	
	@Transactional
	void update(@NotNull Airport airport);
	

	/**
	 * Save.
	 *
	 * @param airport the airport
	 * @return the airport
	 */
	@Transactional
	long save(@NotNull Airport airport);

	/**
	 * Removes the.
	 *
	 * @param id the id
	 */
	@Transactional
	void remove(@NotNull long id);

	/**
	 * Gets the connected.
	 *
	 * @param airportId the airport id
	 * @param direction the direction
	 * @param limit the limit
	 * @param skip the skip
	 * @return the connected
	 */
	@NotNull
	Set<Airport> getConnected(@Min(0) long airportId,
			@NotNull Direction direction, @Min(1) int limit, @Min(0) int skip);

	
	/**
	 * Find nearby.
	 *
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param distanceKm the distance km
	 * @return the list
	 */
	@NotNull
	List<Airport> findNearby(@Range(min = 0, max = 90) double latitude,
			@Range(min = -180, max = 180) double longitude,
			@Min(1) double distanceKm, boolean excludeFirst);
	
	/**
	 * Search.
	 *
	 * @param startsWith the starts with
	 * @param limit the limit
	 * @param skip the skip
	 * @return the list
	 */
	@NotNull
	List<Airport> search(@NotNull String startsWith, @Min(1) int limit,
			@Min(0) int skip);
	
	boolean exists(Long id);
	
	long count();
}
