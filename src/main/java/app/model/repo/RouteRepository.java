package app.model.repo;

import java.util.List;

import org.neo4j.graphdb.Path;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import app.model.Route;



// TODO: Auto-generated Javadoc
/**
 * The Interface RouteRepository.
 */
public interface RouteRepository extends GraphRepository<Route>{
	
	/**
	 * Gets the all by airline.
	 *
	 * @param airlineId the airline id
	 * @param skip the skip
	 * @param limit the limit
	 * @return the all by airline
	 */
	@Query("start r=relationship(*) where type(r) = 'ROUTE' and r.alid = {0} return r skip {2} limit {1}")
	public List<Route> findAllByAirline(long airlineId, int limit, int skip);
	
	/**
	 * Gets the all by outgoing airport.
	 *
	 * @param airportId the airport id
	 * @param skip the skip
	 * @param limit the limit
	 * @return the all by outgoing airport
	 */
	@Query("start a=node({0}) match a-[r:ROUTE]->b return r skip {2} limit {1}")
	public List<Route> findAllOutByAirport(long airportId, int limit, int skip);
	
	/**
	 * Gets the all by incoming airport.
	 *
	 * @param airportId the airport id
	 * @param skip the skip
	 * @param limit the limit
	 * @return the all by incoming airport
	 */
	@Query("start a=node({0}) match b-[r:ROUTE]->a return r skip {2} limit {1}")
	public List<Route> findAllInByAirport(long airportId, int limit, int skip);
	
	/**
	 * Gets the all by airport.
	 *
	 * @param airportId the airport id
	 * @param skip the skip
	 * @param limit the limit
	 * @return the all by airport
	 */
	@Query("start a=node({0}) match a-[r:ROUTE]-b return r skip {2} limit {1}")
	public List<Route> findAllByAirport(long airportId, int limit, int skip);
	
	/**
	 * Gets the all.
	 *
	 * @param skip the skip
	 * @param limit the limit
	 * @return the all
	 */
	@Query("start r=relationship(*) where type(r) = 'ROUTE' return r skip {1} limit {0}")
	public List<Route> findAll(int limit, int skip);
	
	@Query("start a=node({0}), b=node({1}) match path = a-[:ROUTE]->b return path limit 1")
	public List<Path> findDirectPath(long startId, long endId);
	
}
