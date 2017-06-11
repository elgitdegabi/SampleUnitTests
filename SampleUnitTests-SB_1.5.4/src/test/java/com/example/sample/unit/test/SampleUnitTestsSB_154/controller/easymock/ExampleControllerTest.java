package com.example.sample.unit.test.SampleUnitTestsSB_154.controller.easymock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.sample.unit.test.SampleUnitTestsSB_154.controller.ExampleController;
import com.example.sample.unit.test.SampleUnitTestsSB_154.service.ExampleService;

@RunWith(EasyMockRunner.class)
public class ExampleControllerTest {

	// instantiates the subject of test and injects the mocked dependencies
	@TestSubject
	ExampleController exampleController = new ExampleController();
	
	// mock the autowired dependency of the test subject
	@Mock
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
		// mocks the expected behavior for the service in this test case
		// important: you will test only the logic and the coverage of the test subject so mock the dependecies for that
		EasyMock.expect(exampleServiceMock.getAllNames()).andReturn(expectedNames);
		// indicates to EasyMock that has to "record" the expected behavior of mock objects
		EasyMock.replay(exampleServiceMock);
		
		// invokes the test subject that uses the mocked behaviors		
		ResponseEntity<Map<String,String>> result = exampleController.getAllNames();
		
		// assert the expected results of the test case
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody().size(), equalTo(expectedNames.size()));
		assertThat(result.getBody().get("1"), equalTo(expectedNames.get("1"))); // should be "Name 1"
		assertThat(result.getBody().get("1"), equalTo("Name 1")); // another way for the previous assertion
		assertThat(result.getBody().get("2"), equalTo(expectedNames.get("2")));
		assertThat(result.getBody().get("3"), equalTo(expectedNames.get("3")));
		assertThat(result.getBody().get("4"), equalTo(expectedNames.get("4")));
		assertThat(result.getBody().get("5"), equalTo(expectedNames.get("5")));
		
		// indicates to EasyMock that verifies if the mocked objects were called
		EasyMock.verify(exampleServiceMock);
	}

	@Test
	public void getAllNamesWithValidArgumentsShouldReturnAResponseEntityWithErrorAndHttpStatusBadRequestWhenExampleServiceFails() throws Exception {
		SQLException expectedException = new SQLException("Some Expected Exception");
		
		// forces to throw an exception from the service to test the controller's catch code
		EasyMock.expect(exampleServiceMock.getAllNames()).andThrow(expectedException);
		EasyMock.replay(exampleServiceMock);
		
		// invokes the test subject that uses the mocked behaviors		
		ResponseEntity<Map<String,String>> result = exampleController.getAllNames();
		
		// assert the expected results of the test case
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody().size(), equalTo(1));
		// should be the message of the service exception
		assertThat(result.getBody().get("ERROR"), equalTo(expectedException.getMessage()));

		EasyMock.verify(exampleServiceMock);
	}	
}
