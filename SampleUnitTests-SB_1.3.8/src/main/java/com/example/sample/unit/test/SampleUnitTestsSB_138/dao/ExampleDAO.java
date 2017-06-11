package com.example.sample.unit.test.SampleUnitTestsSB_138.dao;

import java.sql.SQLException;
import java.util.Map;

public interface ExampleDAO {

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	Map<String,String> findAll() throws SQLException;
}
