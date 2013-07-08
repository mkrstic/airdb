package app.web.controller;


import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import app.model.Route;
import app.service.AirlineService;
import app.service.RouteService;
import app.web.model.CollectionWrapper;
import app.web.model.ResourceNotFoundException;
import app.web.validator.AirlineValidator;

import com.google.common.collect.Lists;

// TODO: Auto-generated Javadoc
/**
 * The Class AirlineController.
 */
@Controller
@RequestMapping("/avioprevoznici")
public class AirlineController {

	/** The service. */
	@Autowired
	private transient AirlineService service;
	
	/** The route service. */
	@Autowired
	private transient RouteService routeService;
	
	/** The message source. */
	@Autowired
	private transient MessageSource messageSource;
	
	/** The validator. */
	@Autowired
	private transient AirlineValidator validator;
	
	/**
	 * Gets the airline object.
	 *
	 * @return the airline object
	 */
	@ModelAttribute("airline")
	private Airline getAirlineObject() {
		return new Airline();
	}
	
	/**
	 * Index.
	 *
	 * @return the string
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index() {
		return "airlines_list";
	}
	
	
	/**
	 * Creates the.
	 *
	 * @param airline the airline
	 * @param result the result
	 * @param redirectAttrs the redirect attrs
	 * @param locale the locale
	 * @param modelMap the model map
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String create(@ModelAttribute("airline") Airline airline, BindingResult result, RedirectAttributes redirectAttrs, Locale locale, ModelMap modelMap) {
		String view;
		validator.validate(airline, result);
		if (result.hasErrors()) {
			modelMap.addAttribute("resultMessage", messageSource.getMessage("general.form.error", null, locale));
			modelMap.addAttribute("messageClass", "error");
			view = "airlines_add";
		} else {
			service.save(airline);
			redirectAttrs.addFlashAttribute("messageClass", "info");
			redirectAttrs.addFlashAttribute("resultMessage", messageSource.getMessage("airline.created", null, locale));
			view = "redirect:/avioprevoznici/"+airline.getId();
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
		return "airlines_add";
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
		modelMap.addAttribute("airlineId", id);
		modelMap.addAttribute("mainTabActive", "class=\"k-state-active\"");
		return "airlines_airline";
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
			redirectAttrs.addFlashAttribute("resultMessage", messageSource.getMessage("airline.deleted", null, locale));
			return "redirect:/avioprevoznici";
		} catch (IllegalArgumentException ex) {
			throw new ResourceNotFoundException();
		}
	}
	
	/**
	 * Update.
	 *
	 * @param id the id
	 * @param airline the airline
	 * @param result the result
	 * @param redirectAttrs the redirect attrs
	 * @param locale the locale
	 * @param modelMap the model map
	 * @return the string
	 */
	@RequestMapping(value = "/{id:[0-9]*}", method = RequestMethod.PUT) 
	public String update(@PathVariable long id, @ModelAttribute("airline") Airline airline, BindingResult result, RedirectAttributes redirectAttrs, Locale locale, ModelMap modelMap) {
		String view;
		validator.validate(airline, result);
		if (result.hasErrors()) {
			modelMap.addAttribute("airlineId", airline.getId());
			modelMap.addAttribute("editTabActive", "class=\"k-state-active\"");
			modelMap.addAttribute("resultMessage", messageSource.getMessage("general.form.error", null, locale));
			modelMap.addAttribute("messageClass", "error");
			view = "airlines_airline";
		} else {
			service.save(airline);
			redirectAttrs.addFlashAttribute("resultMessage", messageSource.getMessage("general.form.updated", null, locale));
			redirectAttrs.addFlashAttribute("messageClass", "info");
			view = "redirect:/avioprevoznici/"+airline.getId();
		}
		return view;
	}
	
	
	/**
	 * Destroy routes.
	 *
	 * @param id the id
	 * @param routeIds the route ids
	 * @param redirectAttrs the redirect attrs
	 * @param locale the locale
	 * @param modelMap the model map
	 * @return the string
	 */
	@RequestMapping(value = "/{id:[0-9]*}/letovi", method = RequestMethod.DELETE)
	public String destroyRoutes(@PathVariable long id, @RequestParam("routeIds[]") Long[] routeIds, RedirectAttributes redirectAttrs, Locale locale, ModelMap modelMap) {
		if (!service.exists(id)) {
			throw new ResourceNotFoundException();
		}
		routeService.removeAll(Arrays.asList(routeIds));
		redirectAttrs.addFlashAttribute("editTabActive", "class=\"k-state-active\"");
		redirectAttrs.addFlashAttribute("resultMessage", messageSource.getMessage("routes.deleted", null, locale));
		redirectAttrs.addFlashAttribute("messageClass", "info");
		return "redirect:/avioprevoznici/"+id;
	}
	
	/**
	 * Gets the all json.
	 *
	 * @param startsWith the starts with
	 * @param getAll the get all
	 * @param limit the limit
	 * @param skip the skip
	 * @return the all json
	 */
	@RequestMapping(method = RequestMethod.GET, params = { "format=json" }, headers = {"content-type=application/json"})
	public @ResponseBody
	CollectionWrapper<Airline> jsonGetAll(
			@RequestParam(required = false, defaultValue = "") String startsWith,
			@RequestParam(required = false, defaultValue = "false") boolean getAll,
			@RequestParam(required = false, defaultValue = "100") int limit,
			@RequestParam(required = false, defaultValue = "0") int skip) {
		List<Airline> airlines;
		if (StringUtils.isBlank(startsWith)) {
			airlines = service.findAll(limit, skip);
		} else {
			airlines = service.search(startsWith, limit, skip);
		}
		return new CollectionWrapper<Airline>(airlines, airlines.size(), service.count());
	}
	

	
	/**
	 * Gets the json.
	 *
	 * @param id the id
	 * @return the json
	 */
	@RequestMapping(value = "/{id:[0-9]*}", method = RequestMethod.GET, params = { "format=json" }, headers = {"content-type=application/json"})
	public @ResponseBody
	Airline jsonGet(@PathVariable long id) {
		Airline airline = service.find(id);
		if (airline == null) {
			throw new IllegalArgumentException("airline not found");
		}
		return airline;
	}
	
	/**
	 * Gets the routes json.
	 *
	 * @param id the id
	 * @param limit the limit
	 * @param skip the skip
	 * @param lazy the lazy
	 * @return the routes json
	 */
	@RequestMapping(value = "/{id:[0-9]*}/letovi", method = RequestMethod.GET, params = { "format=json" }, headers = { "content-type=application/json" })
	public @ResponseBody
	CollectionWrapper<Route> jsonGetRoutes(
			@PathVariable long id,
			@RequestParam(required = false, defaultValue = "100") int limit,
			@RequestParam(required = false, defaultValue = "0") int skip,
			@RequestParam(required = false, defaultValue = "true") boolean lazy) {
		List<Route> routes = routeService.findAllByAirline(id, limit, skip, lazy);
		return new CollectionWrapper<Route>(routes, routes.size(), routeService.count());
	}
	
	@RequestMapping(value = "/ucitaj", method = RequestMethod.GET, params = { "format=json" }, headers = { "content-type=application/json" })
	public @ResponseBody
	List<Airline> loadAirlines(@RequestParam("ids[]") List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			throw new IllegalArgumentException("no id-s");
		}
		return Lists.newLinkedList(service.findAll(ids));
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
