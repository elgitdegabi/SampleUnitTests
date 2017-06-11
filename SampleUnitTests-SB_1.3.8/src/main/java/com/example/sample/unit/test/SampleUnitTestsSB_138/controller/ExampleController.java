package com.example.sample.unit.test.SampleUnitTestsSB_138.controller;

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

import com.example.sample.unit.test.SampleUnitTestsSB_138.service.ExampleService;

/**
 * 
 * @author Gabriel
 *
 */
@RestController
public class ExampleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExampleController.class);
	
	@Autowired
	ExampleService exampleService;
	
	/**
	 * 
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
