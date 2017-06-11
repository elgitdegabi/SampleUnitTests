package com.example.sample.unit.test.SampleUnitTestsSB_138.service.easymock;

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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import com.example.sample.unit.test.SampleUnitTestsSB_138.dao.ExampleDAO;
import com.example.sample.unit.test.SampleUnitTestsSB_138.service.ExampleService;

@RunWith(EasyMockRunner.class)
public class ExampleServiceTest {
	
	// instantiates the subject of test and injects the mocked dependencies
	@TestSubject
	ExampleService exampleService = new ExampleService();
	
	// mock the autowired dependency of the test subject
	@Mock
	ExampleDAO exampleDAOMock;

	// used to assert and catch the expected exceptions thrown by the test subject
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	// expect values to be returned by the service
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
	public void getAllNamesWithValidArgumentsShouldReturnAMapWithAllNames() throws Exception {
		// you can use in static way or do a static import
		// mocks the expected behavior for the DAO in this test case
		// important: you will test only the logic and the coverage of the test subject so mock the dependecies for that
		EasyMock.expect(exampleDAOMock.findAll()).andReturn(expectedNames);
		// indicates to EasyMock that has to "record" the expected behavior of mock objects
		EasyMock.replay(exampleDAOMock);
		
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
		
		// indicates to EasyMock that verifies if the mocked objects were called
		EasyMock.verify(exampleDAOMock);
	}

	@Test
	public void getAllNamesWithValidArgumentsShouldThrowASQLExceptionWhenDAOThrowsAnSQLException() throws Exception {
		SQLException expectedException = new SQLException("Some Expected DAO Exception");

		// should be before the test subject method invocation
		// indicates that the test case expects an exception of SQLException class with this message
		thrown.expect(SQLException.class);
		thrown.expectMessage(expectedException.getMessage());
				
		// forces to throw an exception from the service to test the controller's catch code
		EasyMock.expect(exampleDAOMock.findAll()).andThrow(expectedException);
		EasyMock.replay(exampleDAOMock);
		
		// invokes the test subject that uses the mocked behaviors		
		// don't expect any assert because the service method should thrown an Exception
		exampleService.getAllNames();
				
		EasyMock.verify(exampleDAOMock);
	}	
}
