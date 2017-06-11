package com.example.sample.unit.test.SampleUnitTestsSB_154.controller.mvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.sample.unit.test.SampleUnitTestsSB_154.controller.ExampleController;
import com.example.sample.unit.test.SampleUnitTestsSB_154.service.ExampleService;

import net.minidev.json.JSONObject;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class) // since 1.4.X version we can use this annotation to run the test using SB features
@WebMvcTest(ExampleController.class) // performs a mvc unit test using the SB context (you can configure one for test purpose)
public class ExampleControllerTest {

	// Spring Boot class to perform restful operations
	@Autowired
	private MockMvc mockMVC;
	 
	// mock the autowired dependency of the test subject		
	@MockBean
	ExampleService exampleServiceMock;

	// expect values to be returned by the controller
	private Map<String,String> expectedNames;
	
	// executed before each test case method -> initializes the expected values
	@Before
	public void setUp() {
		expectedNames = new HashMap<>();
		
		for (int i = 1; i < 6; i++) {
			expectedNames.put(String.valueOf(i), "Name "+ i);
		}
	}
	
	@Test
	public void getAllNamesWithValidArgumentsShouldReturnAResponseEntityWithMapOfNamesAndHttpStatusOK() throws Exception {
		// you can use in static way or do a static import
		// mocks the DAO behavior to return the expected names list
		Mockito.doReturn(expectedNames).when(exampleServiceMock).getAllNames();

		this.mockMVC.perform(get("/test/get"))
				.andExpect(status().isOk())
	            .andExpect(content().string(new JSONObject(expectedNames).toJSONString()));

		// indicates to Mockito that verifies if the mocked objects were called
		Mockito.verify(exampleServiceMock).getAllNames();
	}

	@Test
	public void getAllNamesWithValidArgumentsShouldReturnAResponseEntityWithErrorAndHttpStatusBadRequestWhenExampleServiceFails() throws Exception {		
		// forces to throw an exception from the service to test the controller's catch block
		SQLException expectedException = new SQLException("Some Expected Exception");
		Mockito.doThrow(expectedException).when(exampleServiceMock).getAllNames();

		this.mockMVC.perform(get("/test/get"))
				.andExpect(status().isBadRequest())
	            .andExpect(content().string("{\"ERROR\":\"" +expectedException.getMessage()+ "\"}"));

		Mockito.verify(exampleServiceMock).getAllNames();		
	}
}
