package com.example.sample.unit.test.SampleUnitTestsSB_138.controller.mockito;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.sample.unit.test.SampleUnitTestsSB_138.controller.ExampleController;
import com.example.sample.unit.test.SampleUnitTestsSB_138.service.ExampleService;

import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;

// we don't have the @RunWith(SpringRunner.class) annotation -> we use the pure Mockito annotations
public class ExampleControllerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	// instantiates the subject of test and injects the mocked dependencies
	@InjectMocks // use to inject mocked objects in spring boot versions lower than 1.4
	private ExampleController exampleController;
	
	// mock the autowired dependency of the test subject	
	// we don't have the @MockBean in spring boot versions lower than 1.4 -> we use @Mock
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
		
		// initializes the mocked objects and injects the mocks to the object with @InjectMocks 
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getAllNamesWithValidArgumentsShouldReturnAResponseEntityWithMapOfNamesAndHttpStatusOK() throws Exception {
		// you can use in static way or do a static import
		Mockito.doReturn(expectedNames).when(exampleServiceMock).getAllNames();
		
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
		
		// indicates to Mockito that verifies if the mocked objects were called
		Mockito.verify(exampleServiceMock).getAllNames();
	}
	
	@Test
	public void getAllNamesWithValidArgumentsShouldReturnAResponseEntityWithErrorAndHttpStatusBadRequestWhenExampleServiceFails() throws Exception {
		SQLException expectedException = new SQLException("Some Expected Exception");
		
		// forces to throw an exception from the service to test the controller's catch code
		Mockito.doThrow(expectedException).when(exampleServiceMock).getAllNames();
		
		// invokes the test subject that uses the mocked behaviors		
		ResponseEntity<Map<String,String>> result = exampleController.getAllNames();
		
		// assert the expected results of the test case
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody().size(), equalTo(1));
		// should be the message of the service exception
		assertThat(result.getBody().get("ERROR"), equalTo(expectedException.getMessage()));

		Mockito.verify(exampleServiceMock).getAllNames();		
	}
}
