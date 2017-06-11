/** 
 * SampleUnitTests.
 * Copyright (C) 2017 Gabelopment (gabelopment@gmail.com)
 * 
 * This file is part of SampleUnitTests.
 * 
 * SampleUnitTests is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * SampleUnitTests is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with SampleUnitTests.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.example.sample.unit.test.SampleUnitTestsSB_141.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ExampleDAOImpl implements ExampleDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExampleDAOImpl.class);
	
	final static String QUERY_FIND_ALL = " SELECT * FROM TEST ";
	
	@Autowired
	DataSource dataSource;
	
	public Map<String,String> findAll() throws SQLException {
		Map<String,String> records = new HashMap<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(QUERY_FIND_ALL);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				records.put(resultSet.getString(1), resultSet.getString(2));
			}
			
			return records;
		} catch (SQLException sqle) {
			LOGGER.error("Error performing query", sqle);
			throw sqle;
		} finally {
			closeSQLObject(resultSet, ResultSet.class.getName());
			closeSQLObject(preparedStatement, PreparedStatement.class.getName());
			closeSQLObject(connection, Connection.class.getName());
		}
	}
	
	/**
	 * Closes the given SQL AutoCloseable object.
	 * @param sqlObject
	 * @param sqlObjectType
	 */
	private void closeSQLObject(final AutoCloseable sqlObject, final String sqlObjectType) {
		try {
			sqlObject.close();
		} catch (Exception e) {
			LOGGER.warn("Exception closing : {}", sqlObjectType);
		}
	}
}
