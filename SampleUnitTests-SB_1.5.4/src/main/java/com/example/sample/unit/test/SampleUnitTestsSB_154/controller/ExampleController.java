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
package com.example.sample.unit.test.SampleUnitTestsSB_154.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sample.unit.test.SampleUnitTestsSB_154.service.ExampleService;

/**
 * Handles the request for the example. 
 * @author Gabriel
 *
 */
@RestController
public class ExampleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExampleController.class);
	
	@Autowired
	ExampleService exampleService;
	
	/**
	 * Gets all configured names.
	 * @return
	 */
	@RequestMapping(path="/test/get", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String,String>> getAllNames() {
		LOGGER.info("getAllNames - start");
		ResponseEntity<Map<String,String>> responseEntity = null;
		
		try{
			Map<String,String> allNames = exampleService.getAllNames();
			responseEntity = new ResponseEntity<Map<String,String>>(allNames, HttpStatus.OK);
		} catch (Exception e) {
			Map<String,String> error = new HashMap<>();
			error.put("ERROR", e.getMessage());
			responseEntity = new ResponseEntity<Map<String,String>>(error, HttpStatus.BAD_REQUEST);
		}
		
		LOGGER.info("getAllNames - end");
		return responseEntity;		
	}	

}
