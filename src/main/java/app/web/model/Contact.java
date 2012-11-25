package app.web.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

// TODO: Auto-generated Javadoc
/**
 * The Class Contact.
 */
public class Contact {
	
	/** The name. */
	@NotEmpty
	private String name;
	
	/** The email. */
	@NotEmpty
	@Email
	private String email;
	
	/** The message. */
	@NotEmpty
	private String message;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Contact [name=" + name + ", email=" + email + ", message="
				+ message + "]";
	}
	
	

}
