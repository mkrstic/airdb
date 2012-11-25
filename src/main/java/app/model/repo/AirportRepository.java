package app.model.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;
import org.springframework.data.neo4j.repository.SpatialRepository;

import app.model.Airport;



// TODO: Auto-generated Javadoc
/**
 * The Interface AirportRepository.
 */
public interface AirportRepository extends GraphRepository<Airport>, RelationshipOperationsRepository<Airport>, SpatialRepository<Airport>{
	
	/**
	 * Finds the all.
	 *
	 * @param skip the skip
	 * @param limit the limit
	 * @return the all
	 */
	@Query("start n=node:__types__(className='app.model.Airport') return n order by n.name, n.city, n.country skip {1} limit {0}")
	public List<Airport> findAll(int limit, int skip);
	
	/**
	 * Finds the all connected outgoing.
	 *
	 * @param airportId the airport id
	 * @param skip the skip
	 * @param limit the limit
	 * @return the all connected outgoing
	 */
	@Query("start a=node({0}) match a-[:ROUTE]->b return b order by b.name skip {2} limit {1}")
	public Set<Airport> findAllConnectedOut(long airportId, int limit, int skip);
	
	/**
	 * Finds the all connected incoming.
	 *
	 * @param airportId the airport id
	 * @param skip the skip
	 * @param limit the limit
	 * @return the all connected incoming
	 */
	@Query("start a=node({0}) match b-[:ROUTE]->a return b order by b.name skip {2} limit {1}")
	public Set<Airport> findAllConnectedIn(long airportId, int limit, int skip);
	
	/**
	 * Finds the all connected.
	 *
	 * @param airportId the airport id
	 * @param skip the skip
	 * @param limit the limit
	 * @return the all connected
	 */
	@Query("start a=node({0}) match a-[:ROUTE]-b return b order by b.name skip {2} limit {1}")
	public Set<Airport> findAllConnected(long airportId, int limit, int skip);
	
	
	@Query("start n=node({0}) match n-[:LOCATED_IN]-a return a order by a.city skip {2} limit {1}")
	public List<Airport> findAllByCountry(long countryId, int limit, int skip);
	
	@Query("start n=node({0}) match n-[:LOCATED_IN]-a return count(a)")
	public long countByCountry(long countryId);
	
	/**
	 * Search.
	 *
	 * @param startsWith the starts with
	 * @param skip the skip
	 * @param limit the limit
	 * @return the list
	 */
	@Query("start n=node:__types__(className='app.model.Airport') where n.name =~ {0} or n.city =~ {0} or n.country =~ {0} return n order by n.name, n.city, n.country skip {2} limit {1}")
	public List<Airport> search(String startsWith, int limit, int skip);
	
}
