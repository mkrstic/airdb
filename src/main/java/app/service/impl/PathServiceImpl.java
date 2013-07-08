package app.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphalgo.CommonEvaluators;
import org.neo4j.graphalgo.CostEvaluator;
import org.neo4j.graphalgo.EstimateEvaluator;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Expander;
import org.neo4j.graphdb.Relationship;
import org.neo4j.kernel.Traversal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;

import app.model.Airport;
import app.model.PathBean;
import app.model.RelTypes;
import app.model.Route;
import app.model.repo.RouteRepository;
import app.service.PathService;

public class PathServiceImpl implements PathService {

	@Autowired
	private transient Neo4jTemplate neo4jCore;
	
	@Autowired
	private transient RouteRepository repo;

	private final Expander defaultExpander = Traversal.expanderForTypes(
			DynamicRelationshipType.withName(RelTypes.ROUTE),
			Direction.OUTGOING);

	
	private PathBean buildPath(org.neo4j.graphdb.Path rawPath, boolean lazy) {
		PathBean path;
		if (rawPath == null) {
			path = null;
		} else {
			path = new PathBean();
			int price = 0;
			int distance = 0;
			List<Route> routes = new LinkedList<Route>();
			for (Relationship rel : rawPath.relationships()) {
				Route route = neo4jCore.projectTo(rel, Route.class);
				if (!lazy) {
					neo4jCore.fetch(route.getSource());
					neo4jCore.fetch(route.getDest());
				}
				price += route.getPrice();
				distance += route.getDistance();
				routes.add(route);
			}
			path.setStart(neo4jCore.projectTo(rawPath.startNode(),
					Airport.class));
			path.setEnd(neo4jCore.projectTo(rawPath.endNode(), Airport.class));
			path.setRoutes(routes);
			path.setLength(rawPath.length());
			path.setPrice(price);
			path.setDistance(distance);
		}
		return path;
	}
	
	@Override
	public PathBean findShortestPath(long startId, long endId, int maxDepth, boolean lazy) {
		final PathFinder<org.neo4j.graphdb.Path> finder = GraphAlgoFactory.shortestPath(
				defaultExpander, maxDepth);
		org.neo4j.graphdb.Path rawPath = finder.findSinglePath(neo4jCore.getNode(startId),
				neo4jCore.getNode(endId));
		return buildPath(rawPath, lazy);
	}

	@Override
	public PathBean findOptimisticPath(long startId, long endId, boolean lazy) {
		final EstimateEvaluator<Double> estimateEval = CommonEvaluators
				.geoEstimateEvaluator("latitude", "longitude");
		final CostEvaluator<Double> costEval = CommonEvaluators
				.doubleCostEvaluator("price");
		PathFinder<WeightedPath> finder = GraphAlgoFactory.aStar(
				defaultExpander, costEval, estimateEval);
		WeightedPath rawPath = finder.findSinglePath(neo4jCore.getNode(startId),
				neo4jCore.getNode(endId));
		return buildPath(rawPath, lazy);
	}

	@Override
	public PathBean findCheapestPath(long startId, long endId,
			String costProperty, boolean lazy) {
		final PathFinder<WeightedPath> finder = GraphAlgoFactory.dijkstra(
				defaultExpander, costProperty);
		WeightedPath rawPath = finder.findSinglePath(neo4jCore.getNode(startId),
				neo4jCore.getNode(endId));
		return buildPath(rawPath, lazy);
	}

	@Override
	public PathBean findDirectPath(long startId, long endId, boolean lazy) {
		List<org.neo4j.graphdb.Path> rawPaths = repo.findDirectPath(startId, endId);
		return rawPaths.isEmpty() ? null : buildPath(rawPaths.get(0), lazy); 
	}
}
