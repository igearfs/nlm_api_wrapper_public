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

import com.igearfs.nlm.umls.rxnorm.RxNormService;
import com.igearfs.nlm.umls.spring.AppConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for RxNormService.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class RxNormServiceTest
{
	
	private static final Logger logger = LoggerFactory.getLogger(RxNormFacadeTest.class);
	
	@Autowired
	private RxNormService service;
	
	@Test
	public void testFilterByProperty()
			throws Exception
	{
		// Test filterByProperty method
		String rxcui = "7052";
		String propName = "TTY";
		String propValues = "IN+PIN";
		String filterResponse = service.filterByProperty(rxcui, propName, propValues, "json");
		
		// Assert that the response is not null
		assertNotNull(filterResponse, "Response from filterByProperty should not be null");
		logger.info("Response from filterByProperty:");
		logger.info(filterResponse);
	}
	
	@Test
	public void testFindRelatedNDCs()
			throws Exception
	{
		
		// Test findRelatedNDCs method
		String ndc = "0015-7403-20"; // Example NDC for testing
		String relation = "drug"; // Relation type
		String ndcStatus = "active"; // NDC status
		String format = "json"; // Desired format
		String relatedNDCResponse = service.findRelatedNDCs(ndc, relation, ndcStatus, format);
		
		// Assert that the response is not null
		assertNotNull(relatedNDCResponse, "Response from findRelatedNDCs should not be null");
		logger.info("Response from findRelatedNDCs:");
		logger.info(relatedNDCResponse);
	}
}