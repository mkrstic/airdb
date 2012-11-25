package app.web.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import app.model.Airline;

// TODO: Auto-generated Javadoc
/**
 * The Class AirlineValidator.
 */
public class AirlineValidator implements Validator {

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> classInstance) {
		return Airline.class.equals(classInstance);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "general.name.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "general.country.empty");
		final Airline airline = (Airline)obj;
		if (StringUtils.length(airline.getIata()) > 4) {
			errors.rejectValue("iata", "general.iata_icao.length");
		}
		if (StringUtils.length(airline.getIcao()) > 4) {
			errors.rejectValue("icao", "general.iata_icao.length");
		} 
		
		
	}

}
