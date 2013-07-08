package app.web.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.web.model.Contact;
import app.web.validator.ContactValidator;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeController.
 */
@Controller
public class HomeController {

	/** The contact valdiator. */
	@Autowired
	private transient ContactValidator contactValdiator;
	
	/** The message source. */
	@Autowired
	private transient MessageSource messageSource;
	
	/**
	 * Gets the contact object.
	 *
	 * @return the contact object
	 */
	@ModelAttribute("contact")
	public Contact getContactObject() {
		return new Contact();
	}
	
	/**
	 * Show home page.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value= "/", method = RequestMethod.GET)
	public String showHome(ModelMap model) {
		return "home";
	}
	
	/**
	 * Show about page.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "/o-radu", method = RequestMethod.GET)
	public String showAbout(ModelMap model) {
		return "about";
	}
	
	/**
	 * New contact.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "/kontakt", method = RequestMethod.GET)
	public String newContact(ModelMap model) {
		return "contact";
	}
	
	/**
	 * Creates the contact.
	 *
	 * @param contact the contact
	 * @param result the result
	 * @param redirectAttrs the redirect attrs
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = "/kontakt", method = RequestMethod.POST)
	public String createContact(@ModelAttribute("contact") Contact contact, BindingResult result, RedirectAttributes redirectAttrs, Locale locale) {
		String view;
		contactValdiator.validate(contact, result);
		if (result.hasErrors()) {
			view = "contact";
		} else {
			redirectAttrs.addFlashAttribute("resultMessage", messageSource.getMessage("contact.processed", null, locale));
			redirectAttrs.addFlashAttribute("messageClass", "success");
			view = "redirect:/kontakt";
		}
		return view;
	}

}
