package app.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import app.model.Airline;
import app.model.Route;
import app.model.repo.AirlineRepository;
import app.service.AirlineService;
import app.service.RouteService;



// TODO: Auto-generated Javadoc
/**
 * The Class AirlineServiceImpl.
 */
public class AirlineServiceImpl implements AirlineService {
	
	/** The repo. */
	@Autowired
	private transient AirlineRepository repo;
	
	@Autowired
	private transient RouteService routeService;
	
		
	/* (non-Javadoc)
	 * @see app.service.AirlineService#get(java.lang.Long)
	 */
	@Override
	public Airline find(long id) {
		return repo.findOne(id);
	}
	
	@Override
	public Iterable<Airline> findAll(Iterable<Long> ids) {
		List<Airline> airlines = new LinkedList<Airline>();
		for (long id: ids) {
			try {
				final Airline airline = repo.findOne(id);
				airlines.add(airline);
			} catch (Exception ex) {
				airlines.add(new Airline());
			}
		}
		return airlines;
	}

	/* (non-Javadoc)
	 * @see app.service.AirlineService#getAll(int, int)
	 */
	@Override
	public List<Airline> findAll(int limit, int skip) {
		return repo.findAll(limit, skip);
	}
	
	/* (non-Javadoc)
	 * @see app.service.AirlineService#search(java.lang.String, int, int)
	 */
	@Override
	public List<Airline> search(String startsWith, int limit, int skip) {
		List<Airline> airlines;
		if (StringUtils.isBlank(startsWith)) {
			airlines = repo.findAll(limit, skip);
		} else {
			final String query = "(?i)".concat(startsWith.trim()).concat(".*");
			airlines = repo.search(query, limit, skip);
		}
		return airlines;
	}
	
	/* (non-Javadoc)
	 * @see app.service.AirlineService#save(app.model.Airline)
	 */
	@Override
	@Transactional
	public Airline save(Airline airline) {
		return repo.save(airline);
	}
	
	/* (non-Javadoc)
	 * @see app.service.AirlineService#remove(java.lang.Long)
	 */
	@Override
	@Transactional
	public void remove(long id) {
		if (!repo.exists(id)) {
			throw new IllegalArgumentException("Airline not found with id " + id);
		}
		List<Route> routes = routeService.findAllByAirline(id, Integer.MAX_VALUE, 0, true);
		for (Route route: routes) {
			route.setAlid(null);
			routeService.save(route);
		}
		repo.delete(id);
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
