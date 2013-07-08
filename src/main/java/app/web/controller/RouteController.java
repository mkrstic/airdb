package app.web.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

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

import app.model.Airline;
import app.model.Airport;
import app.model.Route;
import app.service.AirlineService;
import app.service.AirportService;
import app.service.RouteService;
import app.web.model.CollectionWrapper;
import app.web.model.ResourceNotFoundException;
import app.web.validator.RouteValidator;

// TODO: Auto-generated Javadoc
/**
 * The Class RouteController.
 */
@Controller
@RequestMapping("/letovi")
public class RouteController {

	/** The service. */
	@Autowired
	private transient RouteService service;
	
	/** The airline service. */
	@Autowired
	private transient AirlineService airlineService;
	
	/** The airport service. */
	@Autowired
	private transient AirportService airportService;
	
	/** The message source. */
	@Autowired
	private transient MessageSource messageSource;
	
	/** The validator. */
	@Autowired
	private transient RouteValidator validator;
	
	/**
	 * Gets the route object.
	 *
	 * @return the route object
	 */
	@ModelAttribute("route")
	private Route getRouteObject() {
		final Route route = new Route();
		route.setSource(new Airport());
		route.setDest(new Airport());
		return route;
	}

	/**
	 * Index.
	 *
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		return "routes_list";
	}
	
	/**
	 * Show.
	 *
	 * @param id the id
	 * @param locale the locale
	 * @param modelMap the model map
	 * @return the string
	 */
	@RequestMapping(value = "/{id:[0-9]*}", method = RequestMethod.GET)
	public String show(@PathVariable long id, Locale locale, ModelMap modelMap) {
		if (!service.exists(id)) {
			throw new ResourceNotFoundException();
		}
		modelMap.addAttribute("routeId", id);
		modelMap.addAttribute("mainTabActive", "class=\"k-state-active\"");
		return "routes_route";
	}
	
	/**
	 * New obj.
	 *
	 * @param sourceId the source id
	 * @param destId the dest id
	 * @param airlineId the airline id
	 * @param modelMap the model map
	 * @return the string
	 */
	@RequestMapping(value = "/dodaj", method = RequestMethod.GET)
	public String newObj(
			@RequestParam(required = false) Long sourceId,
			@RequestParam(required = false) Long destId,
			@RequestParam(required = false) Long airlineId,
			ModelMap modelMap) {
		if (sourceId != null) {
			final Airport source = airportService.find(sourceId);
			modelMap.addAttribute("sourceId", sourceId);
			modelMap.addAttribute("sourceName", source.getName());
		}
		if (destId != null) {
			final Airport dest = airportService.find(destId);
			modelMap.addAttribute("destId", destId);
			modelMap.addAttribute("destName", dest.getName());
		}
		if (airlineId != null) {
			final Airline airline = airlineService.find(airlineId);
			modelMap.addAttribute("airlineId", airlineId);
			modelMap.addAttribute("airlineName", airline.getName());
		}
		return "routes_add";
	}
	
	/**
	 * Create.
	 *
	 * @param route the route
	 * @param result the result
	 * @param redirectAttrs the redirect attrs
	 * @param locale the locale
	 * @param modelMap the model map
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.POST) 
	public String create(@ModelAttribute("route") Route route, BindingResult result, RedirectAttributes redirectAttrs, Locale locale, ModelMap modelMap) {
		String view;
		validator.validate(route, result);
		if (result.hasErrors()) {
			modelMap.addAttribute("resultMessage", messageSource.getMessage("general.form.error", null, locale));
			modelMap.addAttribute("messageClass", "error");
			view = "routes_add";
		} else {
			service.save(route);
			redirectAttrs.addFlashAttribute("resultMessage", messageSource.getMessage("route.created", null, locale));
			redirectAttrs.addFlashAttribute("messageClass", "info");
			view = "redirect:/letovi/" + route.getId();
		}
		return view;
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
	@RequestMapping(value = "/{id:[0-9]*}", method = RequestMethod.DELETE)
	public String destroy(@PathVariable long id, RedirectAttributes redirectAttrs, Locale locale, ModelMap modelMap) {
		try {
			service.remove(id);
			redirectAttrs.addFlashAttribute("resultMessage", messageSource.getMessage("route.deleted", null, locale));
			redirectAttrs.addFlashAttribute("messageClass", "info");
			return "redirect:/letovi";
		} catch (Exception ex) {
			throw new ResourceNotFoundException();
		}		
	}
	

	
	/**
	 * Update.
	 *
	 * @param id the id
	 * @param route the route
	 * @param result the result
	 * @param redirectAttrs the redirect attrs
	 * @param locale the locale
	 * @param modelMap the model map
	 * @return the string
	 */
	@RequestMapping(value = "/{id:[0-9]*}", method = RequestMethod.PUT)
	public String update(@PathVariable Long id, @ModelAttribute("route") Route route,
			BindingResult result, RedirectAttributes redirectAttrs, Locale locale, ModelMap modelMap) {
		if (route.getId() != id) {
			throw new ResourceNotFoundException();
		}
		String view;
		validator.validate(route, result);
		if (result.hasErrors()) {
			modelMap.addAttribute("routeId", route.getId());
			modelMap.addAttribute("editTabActive", "class=\"k-state-active\"");
			view = "routes_route";
		} else {
			service.save(route);
			redirectAttrs.addFlashAttribute("resultMessage", messageSource.getMessage("general.form.updated", null, locale));
			redirectAttrs.addFlashAttribute("messageClass", "info");
			view = "redirect:/letovi/" + route.getId();
		}
		return view;
	}
	
	/**
	 * Gets the all json.
	 *
	 * @param limit the limit
	 * @param skip the skip
	 * @param lazy the lazy
	 * @return the all json
	 */
	@RequestMapping(method = RequestMethod.GET,params = { "format=json" }, headers = {"content-type=application/json"})
	public @ResponseBody
	CollectionWrapper<Route> jsonGetAll(
			@RequestParam(required = false, defaultValue = "100") int limit,
			@RequestParam(required = false, defaultValue = "0") int skip,
			@RequestParam(required = false, defaultValue = "true") boolean lazy) {
		final List<Route> routes = service.findAll(limit, skip, lazy);
		return new CollectionWrapper<Route>(routes, routes.size(), service.count());
	}
	
	/**
	 * Gets the json.
	 *
	 * @param id the id
	 * @param lazy the lazy
	 * @return the json
	 */
	@RequestMapping(value = "/{id:[0-9]*}",params = { "format=json" }, method = RequestMethod.GET, headers = {"content-type=application/json"})
	public @ResponseBody
	Route jsonGet(@PathVariable long id,
			@RequestParam(required = false, defaultValue = "true") boolean lazy) {
		final Route route = service.find(id, lazy);
		if (route == null) {
			throw new IllegalArgumentException("route not found");
		}
		return route;
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
