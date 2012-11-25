package app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import app.model.Country;
import app.model.repo.CountryRepository;
import app.service.CountryService;


public class CountryServiceImpl implements CountryService {
	
	@Autowired
	private transient CountryRepository repo;
	
	@Override
	public Country find(long id) {
		return repo.findOne(id);
	}

	@Override
	public Country findByAirport(long airportId) {
		return repo.findByAirport(airportId);
	}

	@Override
	public Country findByName(String name) {
		return repo.findByName(name);
	}

	@Override
	@Cacheable(value = "countriesCache")
	public List<Country> findAll(int limit, int skip) {
		return repo.findAll(limit, skip);
	}
	
	@Override
	public long count() {
		return repo.count();
	}
	
	@Override
	public boolean exists(Long id) {
		if (id == null || id < 0) {
			return false;
		}
		return repo.exists(id);
	}

}
