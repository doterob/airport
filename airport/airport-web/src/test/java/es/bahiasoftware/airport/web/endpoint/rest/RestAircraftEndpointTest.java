package es.bahiasoftware.airport.web.endpoint.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static es.bahiasoftware.airport.model.test.AircraftUtil.aircraft;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import es.bahiasoftware.airport.model.Aircraft;
import es.bahiasoftware.airport.model.AircraftStatus;
import es.bahiasoftware.airport.web.app.AppConfig;

import org.junit.runners.MethodSorters;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppConfig.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
// @Transactional
public class RestAircraftEndpointTest extends AbstractTransactionalJUnit4SpringContextTests {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	private static final MediaType APPLICATION_TEXT_PLAIN = new MediaType(MediaType.TEXT_PLAIN,
			Charset.forName("ISO-8859-1"));

	static {
		MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
	}

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void ping() throws Exception {
		this.mockMvc.perform(get("/aircraft/ping")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_TEXT_PLAIN)).andExpect(content().string("null"));
	}

	@Test
	public void add() throws Exception {

		final String code = "A380";
		final String sent = MAPPER.writeValueAsString(aircraft(code));
		final String expected = MAPPER.writeValueAsString(aircraft(code));

		this.mockMvc.perform(post("/aircraft").content(sent).contentType(APPLICATION_JSON_UTF8)).andDo(print())
				.andExpect(status().is2xxSuccessful()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().json(expected));

		this.mockMvc.perform(get("/aircraft/" + code).contentType(APPLICATION_JSON_UTF8)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().json(expected));
	}

	@Test
	public void retire() throws Exception {

		final String code = "A380";
		final String sent = MAPPER.writeValueAsString(aircraft(code));
		final String expected = MAPPER.writeValueAsString(aircraft(code));
		final String retired = MAPPER.writeValueAsString(aircraft(code, AircraftStatus.RETIRED));

		this.mockMvc.perform(post("/aircraft").content(sent).contentType(APPLICATION_JSON_UTF8)).andDo(print())
				.andExpect(status().is2xxSuccessful()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().json(expected));

		this.mockMvc.perform(post("/aircraft/retire/" + code)).andDo(print()).andExpect(status().is2xxSuccessful());

		this.mockMvc.perform(get("/aircraft/" + code).contentType(APPLICATION_JSON_UTF8)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().json(retired));
	}

	@Test
	public void retire_already_retired() throws Exception {

		final String code = "A380";
		final String sent = MAPPER.writeValueAsString(aircraft(code));
		final String expected = MAPPER.writeValueAsString(aircraft(code));

		this.mockMvc.perform(post("/aircraft").content(sent).contentType(APPLICATION_JSON_UTF8)).andDo(print())
				.andExpect(status().is2xxSuccessful()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().json(expected));

		this.mockMvc.perform(post("/aircraft/retire/" + code)).andDo(print()).andExpect(status().is2xxSuccessful());

		this.mockMvc.perform(post("/aircraft/retire/" + code)).andDo(print()).andExpect(status().is4xxClientError());
	}

	@Test
	public void find_empty() throws Exception {

		this.mockMvc.perform(post("/aircraft/find")).andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(content().string("[]"));
	}

	@Test
	public void find_one() throws Exception {
		
		final String code = "A380";
		final String sent = MAPPER.writeValueAsString(aircraft(code));
		final String expected = MAPPER.writeValueAsString(Arrays.asList(aircraft(code)));
		
		this.mockMvc.perform(post("/aircraft").content(sent).contentType(APPLICATION_JSON_UTF8)).andDo(print());

		this.mockMvc.perform(post("/aircraft/find")).andDo(print())
				.andExpect(status().is2xxSuccessful()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().json(expected));
	}
	
	@Test
	public void find_some() throws Exception {
		
		final List<Aircraft> aircrafts = new ArrayList<Aircraft>();
		
		for (int i = 1; i <= 50; i++) {
		
			final String code = String.format("A3%02d", i);
			final String sent = MAPPER.writeValueAsString(aircraft(code));
			
			this.mockMvc.perform(post("/aircraft").content(sent).contentType(APPLICATION_JSON_UTF8)).andDo(print());
			
			aircrafts.add(aircraft(code));
		}

		final String expected = MAPPER.writeValueAsString(aircrafts);
		
		this.mockMvc.perform(post("/aircraft/find")).andDo(print())
				.andExpect(status().is2xxSuccessful()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().json(expected));
	}

}
