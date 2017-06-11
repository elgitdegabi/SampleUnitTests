package com.example.sample.unit.test.SampleUnitTestsSB_138.dao.mockito;

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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.example.sample.unit.test.SampleUnitTestsSB_138.dao.ExampleDAOImpl;

//we don't have the @RunWith(SpringRunner.class) annotation -> we use the pure Mockito annotations
public class ExampleDAOImplTest {
	
	// include the query because if you change the query accidentally the test case will fail (it's a good checkpoint)
	final static String QUERY_FIND_ALL = " SELECT * FROM TEST ";
	
	// instantiates the subject of test and injects the mocked dependencies
	@InjectMocks // use to inject mocked objects in spring boot versions lower than 1.4
	ExampleDAOImpl exampleDAOImpl;
	
	// mock the autowired dependency of the test subject	
	// we don't have the @MockBean in spring boot versions lower than 1.4 -> we use @Mock
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
		
		// initializes the mocked objects and injects the mocks to the object with @InjectMocks 
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void findAllWithValidArgumentsShouldReturnAMapWithAllNames() throws Exception {
		// you can use in static way or do a static import
		// mocks the expected behavior for the DAO in this test case
		// important: you will test only the logic and the coverage of the test subject so mock the dependencies for that
		Mockito.doReturn(connectionMock).when(dataSourceMock).getConnection();
		Mockito.doReturn(preparedStatementMock).when(connectionMock).prepareStatement(QUERY_FIND_ALL);
		Mockito.doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
		
		// mocks the ResultSet iteration and sets the expect results
		// you can chain the expected behavior of the iteration
		Mockito.doReturn(true).doReturn(true).doReturn(true).doReturn(true).doReturn(true).doReturn(false).when(resultSetMock).next();
		Mockito.doReturn("1").doReturn("2").doReturn("3").doReturn("4").doReturn("5").when(resultSetMock).getString(1);
		Mockito.doReturn(expectedNames.get("1"))
			.doReturn(expectedNames.get("2"))
			.doReturn(expectedNames.get("3"))
			.doReturn(expectedNames.get("4"))
			.doReturn(expectedNames.get("5"))
			.when(resultSetMock).getString(2);
		
		// mocks the finally block
		Mockito.doNothing().when(resultSetMock).close(); // the close method is a void method so you don't expect any result
		Mockito.doNothing().when(preparedStatementMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
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
		
		// indicates to Mockito that verifies if the mocked objects were called
		Mockito.verify(dataSourceMock).getConnection();
		// you should include Mockito.verify for each mocked method and object
	}

	@Test
	public void findAllWithValidArgumentsShouldThrowASQLExceptionWhenGetConnectionThrowsAnException() throws Exception {
		SQLException expectedException = new SQLException("Get Connection Exception");

		// should be before the test subject method invocation
		// indicates that the test case expects an exception of SQLException class with this message
		thrown.expect(SQLException.class);
		thrown.expectMessage(expectedException.getMessage());
				
		// forces to throw an exception from the service to test the controller's catch code
		Mockito.doThrow(expectedException).when(dataSourceMock).getConnection();
		
		// mocks the finally block
		Mockito.doNothing().when(resultSetMock).close(); // the close method is a void method so you don't expect any result
		Mockito.doNothing().when(preparedStatementMock).close();
		Mockito.doNothing().when(connectionMock).close();

		// invokes the test subject that uses the mocked behaviors		
		// don't expect any assert because the DAO method should thrown an Exception
		exampleDAOImpl.findAll();
				
		Mockito.verify(dataSourceMock).getConnection();
		// you should include Mockito.verify for each mocked method and object
	}	
}
