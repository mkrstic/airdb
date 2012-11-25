package app.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Service;

import app.model.Country;

// TODO: Auto-generated Javadoc
/**
 * The Interface AirlineService.
 */
// @Validated
@Service("countryService")
public interface CountryService {

	public Country find(@Min(0) long id);

	public Country findByAirport(long airportId);

	public Country findByName(@NotEmpty String name);

	
	public @NotNull
	List<Country> findAll(@Min(1) int limit, @Min(0) int skip);
	
	public boolean exists(Long id);
	
	public long count();

}








