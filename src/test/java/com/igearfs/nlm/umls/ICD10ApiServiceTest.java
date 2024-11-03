/*
 * National Library of Medicine lookup application.
 *
 * Copyright (c) 2024.  In-Game Event, A Red Flag Syndicate  LLC.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the Modified GPL License.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   Modified GPL License for more details.
 *
 * A Copy of the Modified GPL License is included in the code.
 */

package com.igearfs.nlm.umls;

import com.igearfs.nlm.umls.icd10.ICD10ApiService;
import com.igearfs.nlm.umls.spring.AppConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class ICD10ApiServiceTest
{
	
	private static final Logger logger = LoggerFactory.getLogger(ICD10ApiServiceTest.class);
	
	@Autowired
	private ICD10ApiService service;
	
	
	@AfterEach
	void tearDown()
	{
		// Any necessary cleanup can be done here
	}
	
	@Test
	void testSearchICD10WithValidTerms()
			throws IOException
	{
		service.setTerms("tuberc");
		
		String response = service.searchICD10();
		
		// Check that the response is not null or empty
		assertNotNull(response);
		assertFalse(response.isEmpty());
		logger.info(response);
		// Optionally, you can parse the response and check specific content
		// (e.g., looking for specific ICD codes or descriptions)
		if(response != null) {
			assertTrue(response.contains("Tuberculosis")); // Check for expected content
		}
	}
	
	@Test
	void testSearchICD10WithCaching()
			throws IOException
	{
		service.setTerms("tuberc");
		
		// First request to fetch and cache the response
		String firstResponse = service.searchICD10();
		
		// Second request should return the cached response
		String secondResponse = service.searchICD10();
		
		// Verify both responses are the same
		assertEquals(firstResponse, secondResponse);
	}
	
	@Test
	void testSearchICD10WithInvalidTerms()
	{
		service.setTerms(""); // Set invalid terms
		
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
		{
			service.searchICD10();
		});
		
		assertEquals("Terms parameter is required.", thrown.getMessage());
	}
}

