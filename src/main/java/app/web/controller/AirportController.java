package app.web.controller;


import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.neo4j.graphdb.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.model.Airport;
import app.model.Country;
import app.model.Route;
import app.service.AirportService;
import app.service.CountryService;
import app.service.RouteService;
import app.web.model.CollectionWrapper;
import app.web.model.ResourceNotFoundException;
import app.web.validator.AirportValidator;

// TODO: Auto-generated Javadoc
/**
 * The Class AirportController.
 */
@Controller
@RequestMapping("/aerodromi")
public class AirportController {

	/** The service. */
	@Autowired
	private transient AirportService service;
	
	/** The route service. */
	@Autowired
	private transient RouteService routeService;
	
	/** The message source. */
	@Autowired
	private transient MessageSource messageSource;

	/** The validator. */
	@Autowired
	private transient AirportValidator validator;
	
	@Autowired
	private transient CountryService countryService;
	/**
	 * Gets the airport object.
	 *
	 * @return the airport object
	 */
	@ModelAttribute("airport")
	private Airport getAirportObject() {
		return new Airport();
	}

	
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		return "countries_list";
	}
	
	
	/**
	 * Creates the airport.
	 *
	 * @param airport the airport
	 * @param result the result
	 * @param redirectAttrs the redirect attrs
	 * @param locale the locale
	 * @param modelMap the model map
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String create(@ModelAttribute("airport") Airport airport, BindingResult result, RedirectAttributes redirectAttrs, Locale locale, ModelMap modelMap) {
		String view;
		validator.validate(airport, result);
		if (result.hasErrors()) {
			modelMap.addAttribute("resultMessage", messageSource.getMessage("general.form.error", null, locale));
			modelMap.addAttribute("messageClass", "error");
			view = "airports_add";
		} else {
			service.save(airport);
			redirectAttrs.addFlashAttribute("messageClass", "info");
			redirectAttrs.addFlashAttribute("resultMessage", messageSource.getMessage("airport.created", null, locale));
			view = "redirect:/aerodromi/"+airport.getId();
		}
		return view;
	}
	
	/**
	 * New obj.
	 *
	 * @return the string
	 */
	@RequestMapping(value= "/dodaj", method = RequestMethod.GET)
	public String newObj() {
		return "airports_add";
	}
	
	/**
	 * Show.
	 *
	 * @param id the id
	 * @param locale the locale
	 * @param modelMap the model map
	 * @return the string
	 */
	@RequestMapping(value = "/{id:[0-9]*}", method=RequestMethod.GET)
	public String show(@PathVariable long id, Locale locale, ModelMap modelMap) {
		if (!service.exists(id)) {
			throw new ResourceNotFoundException();
		}
		modelMap.addAttribute("airportId", id);
		modelMap.addAttribute("mainTabActive", "class=\"k-state-active\"");
		return "airports_airport";
	}
	
	/**
	 * Destroy.
	 *
	 * @param id the id
	 * @param redirectAttrs the redirect attrs
	 * @param locale the locale
	 * @param modelMap the model map
	 * @return the string
	 */
	@RequestMapping(value = "/{id:[0-9]*}", method=RequestMethod.DELETE)
	public String destroy(@PathVariable long id, RedirectAttributes redirectAttrs, Locale locale, ModelMap modelMap) {
		try {
			service.remove(id);
			redirectAttrs.addFlashAttribute("messageClass", "info");
			redirectAttrs.addFlashAttribute("resultMessage", messageSource.getMessage("airport.deleted", null, locale));
			return "redirect:/aerodromi";
		} catch (Exception ex) {
			throw new ResourceNotFoundException();
		}		
	}
	
	/**
	 * Update.
	 *
	 * @param id the id
	 * @param airport the airport
	 * @param result the result
	 * @param redirectAttributes the redirect attributes
	 * @param locale the locale
	 * @param modelMap the model map
	 * @return the string
	 */
	@RequestMapping(value = "/{id:[0-9]*}", method = RequestMethod.PUT) 
	public String update(@PathVariable long id, @ModelAttribute("airport") Airport airport, BindingResult result, RedirectAttributes redirectAttributes, Locale locale, ModelMap modelMap) {
		String view;
		validator.validate(airport, result);
		if (result.hasErrors()) {
			modelMap.addAttribute("airportId", airport.getId());
			modelMap.addAttribute("editTabActive", "class=\"k-state-active\"");
			modelMap.addAttribute("resultMessage", messageSource.getMessage("general.form.error", null, locale));
			modelMap.addAttribute("messageClass", "error");
			view = "airports_airport";
		} else {
			service.update(airport);
			redirectAttributes.addFlashAttribute("resultMessage", messageSource.getMessage("general.form.updated", null, locale));
			redirectAttributes.addFlashAttribute("messageClass", "info");
			view = "redirect:/aerodromi/"+airport.getId();
		}
		return view;
	}
	
	
	/**
	 * Destroy routes.
	 *
	 * @param id the id
	 * @param routeIds the route ids
	 * @param redirectAttributes the redirect attributes
	 * @param locale the locale
	 * @param modelMap the model map
	 * @return the string
	 */
	@RequestMapping(value = "/{id:[0-9]*}/letovi", method = RequestMethod.DELETE)
	public String destroyRoutes(@PathVariable long id, @RequestParam("routeIds[]") Long[] routeIds, RedirectAttributes redirectAttributes, Locale locale, ModelMap modelMap) {
		try {
			routeService.removeAll(Arrays.asList(routeIds));
			redirectAttributes.addFlashAttribute("editTabActive", "class=\"k-state-active\"");
			redirectAttributes.addFlashAttribute("resultMessage", messageSource.getMessage("routes.deleted", null, locale));
			redirectAttributes.addFlashAttribute("messageClass", "info");
			return "redirect:/aerodromi/"+id;
		} catch (Exception ex) {
			throw new ResourceNotFoundException();
		}
		
	}
	
	
	/**
	 * Gets the all json.
	 *
	 * @param getAll the get all
	 * @param startsWith the starts with
	 * @param limit the limit
	 * @param skip the skip
	 * @return the all json
	 */
	@RequestMapping(method = RequestMethod.GET,params = { "format=json" }, headers = {"content-type=application/json"}, produces = {"application/json"})
	public @ResponseBody
	CollectionWrapper<Airport> jsonGetAll(
			@RequestParam(required = false, defaultValue = "false") boolean getAll,
			@RequestParam(required = false, defaultValue = "") String startsWith,
			@RequestParam(required = false, defaultValue = "100") int limit,
			@RequestParam(required = false, defaultValue = "0") int skip) {
		CollectionWrapper<Airport> collection;
		if (getAll) {
			final List<Airport> airports = service.findAll();
			collection = new CollectionWrapper<Airport>(airports,
					airports.size(), service.count());
		} else {
			final List<Airport> airports = service.search(startsWith, limit, skip);
			collection = new CollectionWrapper<Airport>(airports, airports.size(), service.count());
		}
		return collection;
	}
	
	
	/**
	 * Gets the json.
	 *
	 * @param id the id
	 * @return the json
	 */
	@RequestMapping(value = "/{id:[0-9]*}", method = RequestMethod.GET, params = { "format=json" }, headers = {"content-type=application/json"}, produces = {"application/json"})
	public @ResponseBody
	Airport jsonGet(@PathVariable long id) {
		Airport airport = service.find(id); 
		if (airport == null) {
			throw new IllegalArgumentException("airport not found");
		}
		return airport;
	}
	
	
	/**
	 * Gets the all connected json.
	 *
	 * @param id the id
	 * @param direction the direction
	 * @param limit the limit
	 * @param skip the skip
	 * @return the all connected json
	 */
	@RequestMapping(value = "/{id:[0-9]*}/aerodromi", method = RequestMethod.GET, params = { "format=json" }, headers = {"content-type=application/json"}, produces = {"application/json"})
	public @ResponseBody
	CollectionWrapper<Airport> jsonGetAllConnected(
			@PathVariable long id,
			@RequestParam(required = false, defaultValue = "both") String direction,
			@RequestParam(required = false, defaultValue = "100") int limit,
			@RequestParam(required = false, defaultValue = "0") int skip) {
		Direction neoDirection = Direction.BOTH;
		if ("in".equalsIgnoreCase(direction)) {
			neoDirection = Direction.INCOMING;
		} else if ("out".equalsIgnoreCase(direction)) {
			neoDirection = Direction.OUTGOING;
		} 
		final Set<Airport> airports = service.getConnected(id, neoDirection, limit, skip);
		return new CollectionWrapper<Airport>(airports, airports.size(), service.count());
	}
	
	/**
	 * Gets the all routes json.
	 *
	 * @param id the id
	 * @param direction the direction
	 * @param limit the limit
	 * @param skip the skip
	 * @param lazy the lazy
	 * @return the all routes json
	 */
	@RequestMapping(value = "/{id:[0-9]*}/letovi", method = RequestMethod.GET, params = { "format=json" }, headers = {"content-type=application/json"}, produces = {"application/json"})
	public @ResponseBody
	CollectionWrapper<Route> jsonGetAllRoutes(
			@PathVariable long id,
			@RequestParam(required = false, defaultValue = "both") String direction,
			@RequestParam(required = false, defaultValue = "100") int limit,
			@RequestParam(required = false, defaultValue = "0") int skip,
			@RequestParam(required = false, defaultValue = "true") boolean lazy) {
		Direction neoDirection = Direction.BOTH;
		if ("in".equalsIgnoreCase(direction)) {
			neoDirection = Direction.INCOMING;
		} else if ("out".equalsIgnoreCase(direction)) {
			neoDirection = Direction.OUTGOING;
		}
		final List<Route> routes = routeService.findAllByAirport(id, neoDirection, limit, skip, lazy);
		Collections.sort(routes, new Comparator<Route>() {
			@Override
			public int compare(Route o1, Route o2) {
				return o1.getSource().getCity().compareTo(o2.getDest().getCity());
			}
		});
		return new CollectionWrapper<Route>(routes, routes.size(), routeService.count());
	}
	
	/**
	 * Gets the all nearby json.
	 *
	 * @param id the id
	 * @param lat the lat
	 * @param lng the lng
	 * @param distance the distance
	 * @return the all nearby json
	 */
	@RequestMapping(value = "/{id:[0-9]*}/blizu", method = RequestMethod.GET, params = { "format=json" }, headers = {"content-type=application/json"}, produces = {"application/json"})
	public @ResponseBody
	CollectionWrapper<Airport> jsonGetAllNearby(@PathVariable long id,
			@RequestParam(required = false) Double lat,
			@RequestParam(required = false) Double lng,
			@RequestParam(required = false, defaultValue = "150") int distance,
			@RequestParam(required = false, defaultValue = "true") boolean excludeFirst) {
		Double latitude, longitude;
		if (lat == null || lng == null) {
			final Airport airport = service.find(id);
			latitude = airport.getLatitude();
			longitude = airport.getLongitude();
		} else {
			latitude = lat.doubleValue();
			longitude = lng.doubleValue();
		}
		List<Airport> nearbyAirports = service.findNearby(latitude, longitude, distance, excludeFirst);
		return new CollectionWrapper<Airport>(nearbyAirports, nearbyAirports.size(), service.count());
	}
	
	@RequestMapping(value = "/{id:[0-9]*}/drzave", method = RequestMethod.GET, params = { "format=json" }, headers = { "content-type=application/json" }, produces = { "application/json" })
	public @ResponseBody
	Country jsonGetCountry(@PathVariable long id) {
		Country country = countryService.findByAirport(id);
		if (country == null) {
			throw new IllegalArgumentException("country for airport " + id
					+ " not found");
		}
		return country;
	}
	@RequestMapping(value = "/ukupno", method = RequestMethod.GET, params = { "format=json" }, headers = { "content-type=application/json" })
	public @ResponseBody
	Long jsonCount() {
		return service.count();
	}
	
	/**
	 * Handle json exception.
	 *
	 * @param ex the ex
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
	 * @param ex the ex
	 * @param request the request
	 * @return the string
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleException(ResourceNotFoundException ex, HttpServletRequest request) {
		return "404";
	}
	
	
}
