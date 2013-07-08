package app.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import app.model.Airport;
import app.model.PathBean;
import app.service.AirportService;
import app.service.PathService;
import app.web.model.CollectionWrapper;
import app.web.model.ResourceNotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Class RouteController.
 */
@Controller
@RequestMapping("/putanje")
public class PathController {

	@Autowired
	private transient PathService service;
	@Autowired
	private transient AirportService airportService;
	
	private List<PathBean> findPath(long sourceId, long destId, final String type, final String costProp, int maxDepth, boolean lazy) {
		PathBean path = service.findDirectPath(sourceId, destId, lazy);
		List<PathBean> list = new ArrayList<PathBean>();
		if (path == null) {
			if ("cheapest".equalsIgnoreCase(type)) {
				path = service.findCheapestPath(sourceId, destId, costProp, lazy);
			} else if ("optimistic".equalsIgnoreCase(type)) {
				path = service.findOptimisticPath(sourceId, destId, lazy);
			} else {
				path = service.findShortestPath(sourceId, destId, maxDepth, lazy);
			}
		}
		if (path != null && path.getLength() <= maxDepth) {
			list.add(path);
		} 
		return list;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String showPaths() {
		return "paths_search";
	}
	
	@RequestMapping(value = "/1/najkraca", params = { "format=json" }, method = RequestMethod.GET, headers = { "content-type=application/json" })
	public @ResponseBody
	CollectionWrapper<PathBean> jsonFindPath(
			@RequestParam String type,
			@RequestParam Long sourceId,
			@RequestParam Long destId,
			@RequestParam(defaultValue = "99", required = false) int maxDepth,
			@RequestParam(defaultValue = "price", required = false) String costProp,
			@RequestParam(defaultValue = "true", required = false) boolean lazy) {
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();
		List<PathBean> pathBeans = findPath(sourceId, destId, type, costProp, maxDepth, lazy);
		stopwatch.stop();
		return new CollectionWrapper<PathBean>(pathBeans, pathBeans.size(), pathBeans.size(), stopwatch.getTime());
	}

	@RequestMapping(value = "/1/alternativna", params = { "format=json" }, method = RequestMethod.GET, headers = { "content-type=application/json" })
	public @ResponseBody
	CollectionWrapper<PathBean> jsonFindAltPath(
			@RequestParam double sourceLat,
			@RequestParam double sourceLng,
			@RequestParam double destLat,
			@RequestParam double destLng,
			@RequestParam String type,
			@RequestParam(defaultValue = "99", required = false) int maxDepth,
			@RequestParam(defaultValue = "price", required = false) String costProp,
			@RequestParam(defaultValue = "true", required = false) boolean lazy,
			@RequestParam(defaultValue = "50", required = false) int distanceKm) {
		List<PathBean> pathBeans = new ArrayList<PathBean>();
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();
		final List<Airport> sourceNearby = airportService.findNearby(sourceLat, sourceLng, distanceKm, false);
		final List<Airport> destNearby = airportService.findNearby(destLat, destLng, distanceKm, false);
		stopwatch.suspend();
		for (int i = 0; i < sourceNearby.size() && pathBeans.isEmpty(); i++) {
			for (int j = i; j < destNearby.size() && pathBeans.isEmpty(); j++) {
				final long sourceId = sourceNearby.get(i).getId();
				final long destId = destNearby.get(j).getId();
				if (sourceId == destId) {
					continue;
				}
				stopwatch.resume();
				pathBeans = findPath(sourceId, destId, type, costProp, maxDepth, lazy);
				stopwatch.suspend();
			}
		}
		if (pathBeans.isEmpty()) {
			for (int j = 0; j < destNearby.size() && pathBeans.isEmpty(); j++) {
				for (int i = j; i < sourceNearby.size() && pathBeans.isEmpty(); i++) {
					final long sourceId = sourceNearby.get(i).getId();
					final long destId = destNearby.get(j).getId();
					if (sourceId == destId) {
						continue;
					}
					stopwatch.resume();
					pathBeans = findPath(sourceId, destId, type, costProp, maxDepth, lazy);
					stopwatch.suspend();
				}
			}
		}
		stopwatch.stop();
		return new CollectionWrapper<PathBean>(pathBeans, pathBeans.size(), pathBeans.size(), stopwatch.getTime());
	}
	

	/**
	 * Handle json exception.
	 * 
	 * @param ex
	 *            the ex
	 * @return the string
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody
	String handleJsonException(IllegalArgumentException ex) {
		return ex.getMessage();
	}

	/**
	 * Handle exception.
	 * 
	 * @param ex
	 *            the ex
	 * @param request
	 *            the request
	 * @return the string
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleException(ResourceNotFoundException ex,
			HttpServletRequest request) {
		return "404";
	}

}
