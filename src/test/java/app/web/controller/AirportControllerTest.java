package app.web.controller;

import static org.junit.Assert.fail;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.view;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"file:src/test/resources/config/application-context.xml",
		"file:src/test/resources/config/servlet-context.xml" })
public class AirportControllerTest {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private transient MessageSource messageSource;

	@Autowired
	private AirportController controller;
	private MockMvc mvc;
	private Locale srLocale = new Locale("sr");

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testGet() throws Exception {
		mvc.perform(get("/aerodromi")).andExpect(status().isOk())
				.andExpect(view().name("airports_list"));
	}

	@Test
	public void shoudShow404() throws Exception {
		long airportId = 0;
		mvc.perform(get("/aerodromi/" + airportId));
	}

	@Test
	@Transactional
	public void testCrud() throws Exception {
		MvcResult createResult = mvc
				.perform(
						post("/aerodromi").locale(srLocale)
								.param("name", "Mihajlo Pupin")
								.param("city", "Novi Sad")
								.param("country", "Serbia")
								.param("iata", "NSA2")
								.param("latitude", String.valueOf(45.262231))
								.param("longitude", String.valueOf(19.851858)))
				.andExpect(status().isOk())
				.andExpect(
						flash().attribute(
								"resultMessage",
								messageSource.getMessage("airport.created",
										null, srLocale))).andReturn();
		String viewName = createResult.getModelAndView().getViewName();
		Long airportId = Long.valueOf(viewName.substring(viewName
				.lastIndexOf("/") + 1));
		if (airportId != null) {
			mvc.perform(get("/aerodromi/" + airportId).locale(srLocale))
					.andExpect(status().isOk())
					.andExpect(view().name("airports_airport"));
			mvc.perform(
					put("/aerodromi/" + airportId).locale(srLocale)
							.param("name", "hmm").param("city", "Novi Sad")
							.param("country", "Serbia").param("iata", "NSA2")
							.param("latitude", String.valueOf(45.262231))
							.param("longitude", String.valueOf(19.851858))
							.param("id", String.valueOf(airportId)))
					.andExpect(status().isOk())
					.andExpect(redirectedUrl("/aerodromi/" + airportId));
			mvc.perform(delete("/aerodromi/" + airportId).locale(srLocale))
					.andExpect(status().isOk())
					.andExpect(redirectedUrl("/aerodromi"))
					.andExpect(
							flash().attribute(
									"resultMessage",
									messageSource.getMessage("airport.deleted",
											null, srLocale)));
		} else {
			fail("Airport ID is null");
		}
	}

	@Test
	public void testSearch() throws Exception {
		long startTime = System.currentTimeMillis();
		mvc.perform(
				get("/aerodromi").contentType(MediaType.APPLICATION_JSON)
						.param("format", "json").param("limit", "20")
						.param("startsWith", "beo")).andExpect(status().isOk())
				.andExpect(content().mimeType(MediaType.APPLICATION_JSON));
		long elapsed = System.currentTimeMillis() - startTime;
		System.out.println("Elapsed: " + elapsed + " ms");

		startTime = System.currentTimeMillis();
		mvc.perform(
				get("/aerodromi").contentType(MediaType.APPLICATION_JSON)
						.param("format", "json").param("limit", "100")
						.param("startsWith", "zag")).andExpect(status().isOk())
				.andExpect(content().mimeType(MediaType.APPLICATION_JSON));
		elapsed = System.currentTimeMillis() - startTime;
		System.out.println("Elapsed: " + elapsed + " ms");
	}

	@Test
	public void testSearchFail() throws Exception {
		mvc.perform(
				get("/aerodromi").contentType(MediaType.APPLICATION_JSON)
						.param("format", "json").param("limit", "5")
						.param("startsWith", "slak32jkla"))
				.andExpect(status().isOk())
				.andExpect(content().mimeType(MediaType.APPLICATION_JSON));
	}
}
