package com.example.sample.unit.test.SampleUnitTestsSB_154.dao.easymock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import com.example.sample.unit.test.SampleUnitTestsSB_154.dao.ExampleDAOImpl;

@RunWith(EasyMockRunner.class)
public class ExampleDAOImplTest {
	// include the query because if you change the query accidentally the test case will fail (it's a good checkpoint)
	final static String QUERY_FIND_ALL = " SELECT * FROM TEST ";
	
	// instantiates the subject of test and injects the mocked dependencies
	@TestSubject
	ExampleDAOImpl exampleDAOImpl = new ExampleDAOImpl();
	
	// mocks the autowired dependency of the test subject
	@Mock
	DataSource dataSourceMock;
	// mocks the connection used by the DataSource mock
	@Mock
	Connection connectionMock;
	// mocks the prepared statment used by the DataSource mock
	@Mock
	PreparedStatement preparedStatementMock = null;
	// mocks the result set used by the DataSource mock
	@Mock
	ResultSet resultSetMock = null;

	// used to assert and catch the expected exceptions thrown by the test subject
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	// expect values to be returned by the dao
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
	public void findAllWithValidArgumentsShouldReturnAMapWithAllNames() throws Exception {
		// you can use in static way or do a static import
		// mocks the expected behavior for the DAO in this test case
		// important: you will test only the logic and the coverage of the test subject so mock the dependencies for that
		EasyMock.expect(dataSourceMock.getConnection()).andReturn(connectionMock);
		EasyMock.expect(connectionMock.prepareStatement(QUERY_FIND_ALL)).andReturn(preparedStatementMock);
		EasyMock.expect(preparedStatementMock.executeQuery()).andReturn(resultSetMock);
		
		// mocks the ResultSet iteration and sets the expect results
		EasyMock.expect(resultSetMock.next()).andReturn(true);
		EasyMock.expect(resultSetMock.getString(1)).andReturn("1");
		EasyMock.expect(resultSetMock.getString(2)).andReturn(expectedNames.get("1"));
		EasyMock.expect(resultSetMock.next()).andReturn(true);
		EasyMock.expect(resultSetMock.getString(1)).andReturn("2");
		EasyMock.expect(resultSetMock.getString(2)).andReturn(expectedNames.get("2"));
		EasyMock.expect(resultSetMock.next()).andReturn(true);
		EasyMock.expect(resultSetMock.getString(1)).andReturn("3");
		EasyMock.expect(resultSetMock.getString(2)).andReturn(expectedNames.get("3"));
		EasyMock.expect(resultSetMock.next()).andReturn(true);
		EasyMock.expect(resultSetMock.getString(1)).andReturn("4");
		EasyMock.expect(resultSetMock.getString(2)).andReturn(expectedNames.get("4"));
		EasyMock.expect(resultSetMock.next()).andReturn(true);
		EasyMock.expect(resultSetMock.getString(1)).andReturn("5");
		EasyMock.expect(resultSetMock.getString(2)).andReturn(expectedNames.get("5"));
		EasyMock.expect(resultSetMock.next()).andReturn(false);
		
		// mocks the finally block
		resultSetMock.close(); // the close method is a void method so you don't expect any result
		EasyMock.expectLastCall(); // for the void method use expectLastCall
		preparedStatementMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		// indicates to EasyMock that has to "record" the expected behavior of mock objects
		EasyMock.replay(dataSourceMock, connectionMock, preparedStatementMock, resultSetMock);
		
		// invokes the test subject that uses the mocked behaviors		
		Map<String,String> result = exampleDAOImpl.findAll();
		
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
		EasyMock.verify(dataSourceMock, connectionMock, preparedStatementMock, resultSetMock);
	}

	@Test
	public void findAllWithValidArgumentsShouldThrowASQLExceptionWhenGetConnectionThrowsAnException() throws Exception {
		SQLException expectedException = new SQLException("Get Connection Exception");

		// should be before the test subject method invocation
		// indicates that the test case expects an exception of SQLException class with this message
		thrown.expect(SQLException.class);
		thrown.expectMessage(expectedException.getMessage());
				
		// forces to throw an exception from the service to test the controller's catch code
		EasyMock.expect(dataSourceMock.getConnection()).andThrow(expectedException);
		
		// mocks the finally block
		resultSetMock.close(); // the close method is a void method so you don't expect any result
		EasyMock.expectLastCall(); // for the void method use expectLastCall
		preparedStatementMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();

		EasyMock.replay(dataSourceMock, connectionMock, preparedStatementMock, resultSetMock);
		
		// invokes the test subject that uses the mocked behaviors		
		// don't expect any assert because the DAO method should thrown an Exception
		exampleDAOImpl.findAll();
				
		EasyMock.verify(dataSourceMock);
	}	
}
