package com.bae.carsproject.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.bae.carsproject.data.Cars;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // try random ports until it finds a free one
@AutoConfigureMockMvc
//executes schema then data before the tests
@Sql(scripts = { "classpath:cars-schema.sql",
		"classpath:cars-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test") // sets profile for the test class
public class IntegrationTests {

	@Autowired // tells Spring to inject the MockMVC object into this class
	private MockMvc mockMVC;

	@Autowired
	private ObjectMapper mapper; // yanks the class Spring uses to convert java to JSON

	@Test
	void testCreate() throws Exception {
		// Creating body as this will be tested
		Cars testKit = new Cars("Ferrari", "488 Spider", 2019);
		// convert to json
		String testKitAsJSON = this.mapper.writeValueAsString(testKit);

		System.out.println(testKit);
		System.out.println(testKitAsJSON);

		// body, method, address and the content-type header
		RequestBuilder request = post("/createCar").contentType(MediaType.APPLICATION_JSON).content(testKitAsJSON);

		// check the response body and status

		ResultMatcher checkStatus = status().isCreated();

		Cars testCreatedKit = new Cars("Ferrari", "488 Spider", 2019);
		testCreatedKit.setId(4); // due to the AUTO_INCREMENT
		String testCreatedKitAsJSON = this.mapper.writeValueAsString(testCreatedKit);

		ResultMatcher checkBody = content().json(testCreatedKitAsJSON);
		// SEND request and check status + body
		this.mockMVC.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void testDelete() throws Exception {
		// create request
		RequestBuilder request = delete("/deleteCar/1");

		// check response
		ResultMatcher checkStatus = status().isNoContent();
		ResultMatcher checkBody = content().string("Deleted: 1");

		// Send Request and check status + body
		this.mockMVC.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void testGetAll() throws Exception {

		List<Cars> carsList = new ArrayList<>();
		Cars found0 = new Cars(1, "Audi", "RS6", 2019);
		Cars found = new Cars(2, "Ferrari", "488", 2020);
		Cars foundTwo = new Cars(3, "McLaren", "750LT Spider", 2016);
		carsList.add(found0);
		carsList.add(found);
		carsList.add(foundTwo);
		String listAsJSON = this.mapper.writeValueAsString(carsList);

		// create request
		RequestBuilder request = get("/getAllCars");

		// check response

		ResultMatcher checkStatus = status().isOk();
		ResultMatcher checkBody = content().json(listAsJSON);

		this.mockMVC.perform(request).andExpect(checkStatus).andExpect(checkBody);

	}

	@Test
	void testGetCarById() throws Exception {

		Cars get = new Cars(1, "Audi", "RS6", 2019);
		String getAsJSON = this.mapper.writeValueAsString(get);

		// create request
		RequestBuilder request = get("/getSpecificCar/1");

		// check response
		ResultMatcher checkStatus = status().isOk();

		ResultMatcher checkBody = content().json(getAsJSON);

		this.mockMVC.perform(request).andExpect(checkStatus).andExpect(checkBody);

	}

	@Test
	void testUpdate() throws Exception {
		// Creating body
		Cars testCar = new Cars(1, "Ferrari", "488 Spider", 2019);

		// convert to json
		String testCarAsJSON = this.mapper.writeValueAsString(testCar);

		// create request
		RequestBuilder request = put("/replaceCar/1").contentType(MediaType.APPLICATION_JSON).content(testCarAsJSON);

		// check response body and status

		ResultMatcher checkStatus = status().isAccepted();
		ResultMatcher checkBody = content().json(testCarAsJSON);

		this.mockMVC.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void testgetByBrand() throws Exception {

		List<Cars> carsList = new ArrayList<>();
		Cars found0 = new Cars(1, "Audi", "RS6", 2019);

		carsList.add(found0);

		String listAsJSON = this.mapper.writeValueAsString(carsList);

		// create request
		RequestBuilder request = get("/getByBrand/Audi");

		// check response
		ResultMatcher checkStatus = status().isAccepted();

		ResultMatcher checkBody = content().json(listAsJSON);

		// check response
		this.mockMVC.perform(request).andExpect(checkStatus).andExpect(checkBody);

	}

}
