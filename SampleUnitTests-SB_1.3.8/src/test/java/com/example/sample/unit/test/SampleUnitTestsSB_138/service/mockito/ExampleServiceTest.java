package com.example.sample.unit.test.SampleUnitTestsSB_138.service.mockito;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.example.sample.unit.test.SampleUnitTestsSB_138.dao.ExampleDAO;
import com.example.sample.unit.test.SampleUnitTestsSB_138.service.ExampleService;

import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;

// we don't have the @RunWith(SpringRunner.class) annotation -> we use the pure Mockito annotations
public class ExampleServiceTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	// instantiates the subject of test and injects the mocked dependencies
	@InjectMocks // use to inject mocked objects in spring boot versions lower than 1.4
	private ExampleService exampleService;
	
	// mock the autowired dependency of the test subject	
	// we don't have the @MockBean in spring boot versions lower than 1.4 -> we use @Mock
	@Mock
	ExampleDAO exampleDAOMock;
	
	// expect values to be returned by the service
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
	public void getAllNamesWithValidArgumentsShouldReturnAMapWithAllNames() throws Exception {
		// you can use in static way or do a static import
		Mockito.doReturn(expectedNames).when(exampleDAOMock).findAll();
		
		// invokes the test subject that uses the mocked behaviors		
		Map<String,String> result = exampleService.getAllNames();
		
		// assert the expected results of the test case
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(expectedNames.size()));
		assertThat(result.get("1"), equalTo(expectedNames.get("1"))); // should be "Name 1"
		assertThat(result.get("1"), equalTo("Name 1")); // another way for the previous assertion
		assertThat(result.get("2"), equalTo(expectedNames.get("2")));
		assertThat(result.get("3"), equalTo(expectedNames.get("3")));
		assertThat(result.get("4"), equalTo(expectedNames.get("4")));
		assertThat(result.get("5"), equalTo(expectedNames.get("5")));
		
		// indicates to Mockito that verifies if the mocked objects were called
		Mockito.verify(exampleDAOMock).findAll();
	}
	
	@Test
	public void getAllNamesWithValidArgumentsShouldThrowASQLExceptionWhenDAOThrowsAnSQLException() throws Exception {
		SQLException expectedException = new SQLException("Some Expected DAO Exception");
		
		// should be before the test subject method invocation
		// indicates that the test case expects an exception of SQLException class with this message
		thrown.expect(SQLException.class);
		thrown.expectMessage(expectedException.getMessage());
								
		// forces to throw an exception from the service to test the controller's catch code
		Mockito.doThrow(expectedException).when(exampleDAOMock).findAll();
		
		// invokes the test subject that uses the mocked behaviors		
		// don't expect any assert because the service method should thrown an Exception
		exampleService.getAllNames();
				
		Mockito.verify(exampleDAOMock).findAll();		
	}
}
