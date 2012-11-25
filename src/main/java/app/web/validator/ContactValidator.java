package app.web.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import app.web.model.Contact;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactValidator.
 */
public class ContactValidator implements Validator {

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> c) {
		return Contact.class.equals(c);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "contact.name.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "contact.email.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message", "contact.message.empty");
		final Contact contact = (Contact) obj;
		final javax.validation.Validator hibernateValidator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Contact>> constraintViolations = hibernateValidator.validate(contact);
		if (!constraintViolations.isEmpty()) {
			errors.reject("contact.validation.error");
		}
		
	}

}
