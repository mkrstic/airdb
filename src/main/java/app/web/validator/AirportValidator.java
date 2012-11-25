package app.web.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import app.model.Airport;

// TODO: Auto-generated Javadoc
/**
 * The Class AirportValidator.
 */
public class AirportValidator implements Validator {

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> classInstance) {
		return Airport.class.equals(classInstance);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "general.name.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "general.city.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "general.country.empty");
		final Airport airport = (Airport)obj;
		if (StringUtils.length(airport.getIata()) > 4) {
			errors.rejectValue("iata", "general.iata_icao.length");
		}
		if (StringUtils.length(airport.getIcao()) > 4) {
			errors.rejectValue("icao", "general.iata_icao.length");
		} 
		
		
	}

}
