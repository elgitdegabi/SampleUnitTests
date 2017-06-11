package com.example.sample.unit.test.SampleUnitTestsSB_138.service;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sample.unit.test.SampleUnitTestsSB_138.dao.ExampleDAO;

/**
 * 
 * @author Gabriel
 *
 */
@Service
public class ExampleService {
	
	@Autowired
	ExampleDAO exampleDAO;
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> getAllNames() throws SQLException {
		return exampleDAO.findAll();
	}
}
