/*
 * Copyright (c) 2024. In-Game Event, A Red Flag Syndicate LLC
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the Server Side Public License, version 1, as published by MongoDB, Inc., with the following additional terms:
 *
 * - Any use of this software in a commercial capacity requires a commercial license agreement with In-Game Event, A Red Flag Syndicate LLC. Contact licence_request@igearfs.com for details.
 *
 * - If you choose not to obtain a commercial license, you must comply with the SSPL terms, which include making publicly available the source code for all programs, tooling, and infrastructure used to operate this software as a service.
 *
 * This program is distributed WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Server Side Public License for more details.
 *
 * For licensing inquiries, contact: licence_request@igearfs.com
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