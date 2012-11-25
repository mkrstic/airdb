package app.model.repo;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

import app.model.Airline;


// TODO: Auto-generated Javadoc
/**
 * The Interface AirlineRepository.
 */
public interface AirlineRepository extends GraphRepository<Airline>,
		RelationshipOperationsRepository<Airline> {
		
	/**
	 * Finds the all.
	 *
	 * @param skip the skip
	 * @param limit the limit
	 * @return the all
	 */
	@Query("start n=node:__types__(className='app.model.Airline') return n order by n.name skip {1} limit {0}")
	public List<Airline> findAll(int limit, int skip);
	
	/**
	 * Search.
	 *
	 * @param startsWith the starts with
	 * @param skip the skip
	 * @param limit the limit
	 * @return the list
	 */
	@Query("start n=node:__types__(className='app.model.Airline') where has(n.country) and n.name =~ {0} or n.country =~ {0} return n order by n.name, n.country skip {2} limit {1}")
	public List<Airline> search(String startsWith, int limit, int skip);
	
}
