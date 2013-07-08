package app.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import app.model.Route;
import app.service.AirportService;

// TODO: Auto-generated Javadoc
/**
 * The Class RouteValidator.
 */
public class RouteValidator implements Validator {

	/** The airport service. */
	@Autowired
	private transient AirportService airportService;

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> classInstance) {
		return Route.class.equals(classInstance);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "source.id",
				"route.airports.source.empty");
		ValidationUtils.rejectIfEmpty(errors, "dest.id",
				"route.airports.dest.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "alid",
				"route.alid.empty");
		final Route route = (Route) obj;
		if (!airportService.exists(route.getSource().getId())) {
			errors.reject("source.id", "route.airports.source.invalid");
		} else if (!airportService.exists(route.getDest().getId())) {
			errors.reject("dest.id", "route.airports.dest.invalid");
		} else if (route.getSource().getId().equals(route.getDest().getId())) {
			errors.reject("dest.id", "route.airports.equal");
		}
		if (route.getPrice() < 0) {
			errors.reject("price", "route.price.lowerThanZero");
		}
		if (route.getDistance() < 0) {
			errors.reject("distance", "route.distance.lowerThanZero");
		}

	}

}
