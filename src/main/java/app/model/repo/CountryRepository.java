package app.model.repo;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

import app.model.Country;



// TODO: Auto-generated Javadoc
/**
 * The Interface AirportRepository.
 */
public interface CountryRepository extends GraphRepository<Country>, RelationshipOperationsRepository<Country> {
	
	
	@Query("start n=node:__types__(className='app.model.Country') where n.name = {0} return n limit 1")
	public Country findByName(String name);
	
	@Query("start airport=node({0}) match airport-[:LOCATED_IN]-country return country")
	public Country findByAirport(long airportId);
	
	@Query("start n=node:__types__(className='app.model.Country') return n order by n.name skip {1} limit {0}")
	public List<Country> findAll(int limit, int skip);
	
	
}
