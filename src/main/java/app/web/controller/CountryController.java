package app.web.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import app.model.Airport;
import app.model.Country;
import app.service.AirportService;
import app.service.CountryService;
import app.web.model.CollectionWrapper;
import app.web.model.ResourceNotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Class AirportController.
 */
@Controller
@RequestMapping("/drzave")
public class CountryController {

	@Autowired
	private transient CountryService service;
	@Autowired
	private transient AirportService airportService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "countries_list";
	}
	
	@RequestMapping(value = "/{id:[0-9]*}/aerodromi", method = RequestMethod.GET)
	public String showAirportsBy(@PathVariable long id, ModelMap modelMap) {
		if (!service.exists(id)) {
			throw new ResourceNotFoundException();
		}
		modelMap.addAttribute("countryId", id);
		return "airports_list";
	}
	
	@RequestMapping(value = "/{id:[0-9]*}", method = RequestMethod.GET, params = { "format=json" }, headers = {"content-type=application/json"}, produces = {"application/json"})
	public @ResponseBody
	Country jsonGet(@PathVariable long id) {
		Country country = service.find(id);
		if (country == null) {
			throw new IllegalArgumentException("country not found");
		}
		return country;
	}
	
	@RequestMapping(method = RequestMethod.GET, params = { "format=json" }, headers = {"content-type=application/json"}, produces = {"application/json"})
	public @ResponseBody
	CollectionWrapper<Country> jsonGetAll(
			@RequestParam(required = false, defaultValue = "100") int limit,
			@RequestParam(required = false, defaultValue = "0") int skip) {
		final List<Country> countries = service.findAll(limit, skip);
		return new CollectionWrapper<Country>(countries, countries.size(), service.count());
	}
	
	@RequestMapping(value = "/{id:[0-9]+}/aerodromi", method = RequestMethod.GET,params = { "format=json" }, headers = {"content-type=application/json"}, produces = {"application/json"})
	public @ResponseBody
	CollectionWrapper<Airport> jsonGetAllAirportsBy(
			@PathVariable long id,
			@RequestParam(required = false, defaultValue = "false") boolean getAll,
			@RequestParam(required = false, defaultValue = "100") int limit,
			@RequestParam(required = false, defaultValue = "0") int skip) {
		long count = airportService.countByCountry(id);
		List<Airport> airports;
		if (getAll) {
			airports = airportService.findAllByCountry(id, Integer.MAX_VALUE, 0);
		} else {
			airports = airportService.findAllByCountry(id, limit, skip);
		}
		return new CollectionWrapper<Airport>(airports, airports.size(), count);
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
