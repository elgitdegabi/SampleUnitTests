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
 */package com.example.sample.unit.test.SampleUnitTestsSB_138.service;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sample.unit.test.SampleUnitTestsSB_138.dao.ExampleDAO;

/**
 * Services for the example.
 * @author Gabriel
 *
 */
@Service
public class ExampleService {
	
	@Autowired
	ExampleDAO exampleDAO;
	
	/**
	 * Gets all configured names.
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> getAllNames() throws SQLException {
		return exampleDAO.findAll();
	}
}
