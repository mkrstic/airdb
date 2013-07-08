package app.service.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.neo4j.graphdb.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import app.model.Airport;
import app.model.Country;
import app.model.LocatedIn;
import app.model.RelTypes;
import app.model.repo.AirportRepository;
import app.service.AirportService;
import app.service.CountryService;

import com.google.common.collect.Lists;



// TODO: Auto-generated Javadoc
/**
 * The Class AirportServiceImpl.
 */
public class AirportServiceImpl implements AirportService {
	
	/** The repo. */
	@Autowired
    private transient AirportRepository repo;
	
	@Autowired
	private transient CountryService countryService;

	
	/* (non-Javadoc)
	 * @see app.service.AirportService#get(java.lang.Long)
	 */
	@Override
	public Airport find(long id) {
		return repo.findOne(id);
	}
	
	/* (non-Javadoc)
	 * @see app.service.AirportService#getAll()
	 */
	@Override
	public List<Airport> findAll() {
		return Lists.newArrayList(repo.findAll());
	}
	
	@Override
	public @NotNull
	List<Airport> findAllByCountry(long countryId, int limit, int skip) {
		return repo.findAllByCountry(countryId, limit, skip);
	}
	
	@Override
	public @NotNull
	long countByCountry(long countryId) {
		return repo.countByCountry(countryId);
	}
	
	@Override
	@Transactional
	public void update(Airport airport) {
		if (airport == null || airport.getId() == null) {
			throw new IllegalArgumentException("Airport not exists");
		}
		Country country = countryService.findByAirport(airport.getId());
		if (!airport.getCountry().equals(country.getName())) {
			repo.deleteRelationshipBetween(airport, country, RelTypes.LOCATED_IN);
			Country newCountry = countryService.findByName(airport.getCountry());
			if (newCountry == null) {
				throw new IllegalArgumentException("Unknown country " + airport.getCountry()); 
			}
			airport.setCountryCode(newCountry.getCode());
			repo.createRelationshipBetween(airport, newCountry, LocatedIn.class, RelTypes.LOCATED_IN);
		}
		repo.save(airport);
	}
		
	
	/* (non-Javadoc)
	 * @see app.service.AirportService#save(app.model.Airport)
	 */
	@Override
	@Transactional
	public long save(Airport airport) {
		Country country = countryService.findByName(airport.getCountry());
		if (country == null) {
			throw new IllegalArgumentException("Unknown country " + airport.getCountry());
		}
		repo.createRelationshipBetween(airport, country, LocatedIn.class, RelTypes.LOCATED_IN);
		airport.setCountryCode(country.getCode());
		repo.save(airport);
		return airport.getId();
	}
	
	/* (non-Javadoc)
	 * @see app.service.AirportService#remove(java.lang.Long)
	 */
	@Override
	@Transactional
	public void remove(long id) {
		final Airport airport = repo.findOne(id);
		if (airport == null) {
			throw new IllegalArgumentException("Not found airport with id " + id);
		}
		Country country = countryService.findByAirport(id);
		if (country != null) {
			repo.deleteRelationshipBetween(airport, country, RelTypes.LOCATED_IN);
		}
		for (Airport end : repo.findAllConnected(id, Integer.MAX_VALUE, 0)) {
			repo.deleteRelationshipBetween(airport, end, RelTypes.ROUTE);
		}
		repo.delete(id);		
	}
	
	
	/* (non-Javadoc)
	 * @see app.service.AirportService#getConnected(java.lang.Long, org.neo4j.graphdb.Direction, int, int)
	 */
	@Override
	public Set<Airport> getConnected(long airportId, Direction direction, int limit, int skip) {
		Set<Airport> airports;
		if (direction == Direction.INCOMING) {
			airports = repo.findAllConnectedIn(airportId, limit, skip);
		} else if (direction == Direction.OUTGOING) {
			airports = repo.findAllConnectedOut(airportId, limit, skip);
		} else {
			airports = repo.findAllConnected(airportId, limit, skip);
		}
		return airports;
	}
	

	/* (non-Javadoc)
	 * @see app.service.AirportService#search(java.lang.String, int, int)
	 */
	@Override
	public List<Airport> search(String startsWith, int limit, int skip) {
		List<Airport> airports;
		if (StringUtils.isBlank(startsWith)) {
			airports = repo.findAll(limit, skip);
		} else {
			String query = "(?i)".concat(startsWith.trim()).concat(".*");
			airports = repo.search(query, limit, skip);
		}
		return airports;
	}
	
	/* (non-Javadoc)
	 * @see app.service.AirportService#findNearby(app.model.Airport, double)
	 */
	
	/* (non-Javadoc)
	 * @see app.service.AirportService#findNearby(double, double, double, boolean)
	 */
	@Override
	public List<Airport> findNearby(double latitude, double longitude,
			double distanceKm, boolean excludeFirst) {
		Iterator<Airport> itNearby = repo.findWithinDistance("AirportLocation", longitude, latitude, distanceKm).iterator();
		if (excludeFirst && itNearby.hasNext()) {
			itNearby.next();
		}
		List<Airport> nearbyAirports = new LinkedList<Airport>();
		while (itNearby.hasNext()) {
			try {
				nearbyAirports.add(itNearby.next());
			} catch (Exception ex) {
			}
		}
		return nearbyAirports;
	 
	}
	

	@Override
	public long count() {
		return repo.count();
	}
	
	@Override
	public boolean exists(Long id) {
		if (id == null || id < 0) {
			return false;
		}
		return repo.exists(id);
	}
	

}
