package app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

import app.model.Route;
import app.model.repo.RouteRepository;
import app.service.RouteService;



// TODO: Auto-generated Javadoc
/**
 * The Class RouteServiceImpl.
 */
public class RouteServiceImpl implements RouteService {
	
	/** The repo. */
	@Autowired
	private transient RouteRepository repo;
	
	/** The neo4j core. */
	@Autowired
	private transient Neo4jTemplate neo4jCore;
	
	/* (non-Javadoc)
	 * @see app.service.RouteService#get(java.lang.Long)
	 */
	@Override
	public Route find(long id) {
		return repo.findOne(id);
	}
	
	@Override
	public Route find(long id, boolean lazy) {
		Route route = repo.findOne(id);
		if (route != null && !lazy) {
			neo4jCore.fetch(route.getSource());
			neo4jCore.fetch(route.getDest());
		}
		return route;
	}

	@Override
	public List<Route> findAll(int limit, int skip, boolean lazy) {
		List<Route> routes = repo.findAll(limit, skip);
		if (!lazy) {
			for (Route route : routes) {
				neo4jCore.fetch(route.getSource());
				neo4jCore.fetch(route.getDest());
			}
		}
		return routes;
	}
	
	/* (non-Javadoc)
	 * @see app.service.RouteService#getAllByAirport(java.lang.Long, org.neo4j.graphdb.Direction, int, int, boolean)
	 */
	@Override
	public List<Route> findAllByAirport(long airportId, Direction direction, int limit, int skip, boolean lazy) {
		List<Route> routes = new ArrayList<Route>();
		switch (direction) {
		case INCOMING:
			routes = repo.findAllInByAirport(airportId, limit, skip);
			break;
		case OUTGOING:
			routes = repo.findAllOutByAirport(airportId, limit, skip);
			break;
		default:
			routes = repo.findAllByAirport(airportId, limit, skip);
		}
		if (!lazy) {
			for (Route r : routes) {
				neo4jCore.fetch(r.getSource());
				neo4jCore.fetch(r.getDest());
			}
		}
		return routes;
	}
	
	/* (non-Javadoc)
	 * @see app.service.RouteService#getAllByAirline(java.lang.Long, int, int, boolean)
	 */
	@Override
	public List<Route> findAllByAirline(long airlineId, int limit, int skip, boolean lazy) {
		final List<Route> routes = repo.findAllByAirline(airlineId, limit, skip);
		if (!lazy) {
			for (Route r: routes) {
				neo4jCore.fetch(r.getSource());
				neo4jCore.fetch(r.getDest());
			}
		}
		return routes;
	}
	
		
	/* (non-Javadoc)
	 * @see app.service.RouteService#save(app.model.Route)
	 */
	@Override
	@Transactional
	public Route save(Route route) {
		return repo.save(route);
	}
	
	/* (non-Javadoc)
	 * @see app.service.RouteService#remove(java.lang.Long)
	 */
	@Override
	@Transactional
	public void remove(long id) {
		if (!repo.exists(id)) {
			throw new IllegalArgumentException("Route not found with id " + id);
		}
		repo.delete(id);
	}
	
	
	/* (non-Javadoc)
	 * @see app.service.RouteService#removeAll(java.util.List)
	 */
	@Override
	@Transactional
	public void removeAll(List<Long> idList) {
		for (Long id: idList) {
			repo.delete(id);
		}
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
