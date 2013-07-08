package app.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.model.Airline;

// TODO: Auto-generated Javadoc
/**
 * The Interface AirlineService.
 */
//@Validated
@Service("airlineService")
public interface AirlineService {

	
	/**
	 * Gets the.
	 *
	 * @param id the id
	 * @return the airline
	 */
	Airline find(@Min(0) long id);
		
	@NotNull
	Iterable<Airline> findAll(@NotNull Iterable<Long> ids);
	
	/**
	 * Gets the all.
	 *
	 * @param limit the limit
	 * @param skip the skip
	 * @return the all
	 */
	@NotNull
	List<Airline> findAll(@Min(0) int limit, @Min(0) int skip);

	/**
	 * Search.
	 *
	 * @param startsWith the starts with
	 * @param limit the limit
	 * @param skip the skip
	 * @return the list
	 */
	@NotNull
	List<Airline> search(@NotNull String startsWith,
			@Min(1) int limit, @Min(0) int skip);

	/**
	 * Save.
	 *
	 * @param airline the airline
	 * @return the airline
	 */
	@Transactional
	Airline save(@NotNull Airline airline);

	/**
	 * Removes the.
	 *
	 * @param id the id
	 */
	@Transactional
	void remove(@Min(0) long id);
	
	boolean exists(Long id);
	
	long count();
	
}
