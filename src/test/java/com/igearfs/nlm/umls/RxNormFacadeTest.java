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

import com.igearfs.nlm.umls.rxnorm.RxNormFacade;
import com.igearfs.nlm.umls.rxnorm.dataobject.FilterByPropertyResponse;
import com.igearfs.nlm.umls.rxnorm.dataobject.NDCInfo;
import com.igearfs.nlm.umls.rxnorm.dataobject.RelatedNDCResponse;
import com.igearfs.nlm.umls.spring.AppConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class RxNormFacadeTest
{
	
	private static final Logger logger = LoggerFactory.getLogger(RxNormFacadeTest.class);
	
	@Autowired
	private RxNormFacade facade;
	
	@Test
	public void testFilterByProperty()
			throws Exception
	{
		// Prepare test data
		String rxcui = "7052";
		String propName = "TTY";
		String propValues = "IN+PIN";
		
		// Call the facade method
		FilterByPropertyResponse response = facade.filterByProperty(rxcui, propName, propValues);
		
		logger.info("Response::" + response);
		// Assertions
		assertNotNull(response, "Response from filterByProperty should not be null");
		assertEquals("7052", response.getRxcui());
		assertEquals(null, response.getPropName());
		assertEquals(null, response.getPropValues());
	}
	
	@Test
	public void testFindRelatedNDCs()
			throws Exception
	{
		// Prepare test data
		String ndc = "0015-7403-20";
		String relation = "drug";
		String ndcStatus = "active";
		String format = "json";
		
		// Call the facade method
		RelatedNDCResponse response = facade.findRelatedNDCs(ndc, relation, ndcStatus);
		
		// Assertions
		assertNotNull(response, "Response from findRelatedNDCs should not be null");
		assertNotNull(response.getNdcInfoList(), "NDCInfoList should not be null");
		assertNotNull(response.getNdcInfoList().getNdcInfo(), "NDCInfo should not be null");
		
		// Example assertions based on expected values
		List<NDCInfo> ndcInfos = response.getNdcInfoList().getNdcInfo();
		assertFalse(ndcInfos.isEmpty(), "NDCInfo list should not be empty");
		
		// Adjust based on expected number of results
		assertTrue(ndcInfos.size() > 0, "NDCInfo list should contain results");
		
		// Check the properties of each NDCInfo object in the list
		for (NDCInfo ndcInfo : ndcInfos)
		{
			assertNotNull(ndcInfo.getNdc11(), "NDC11 should not be null");
			assertNotNull(ndcInfo.getRxcui(), "Rxcui should not be null");
			assertNotNull(ndcInfo.getStatus(), "Status should not be null");
			assertNotNull(ndcInfo.getConceptName(), "Concept name should not be null");
			assertEquals("ACTIVE", ndcInfo.getStatus(), "Expected status should match"); // Example check for status
			// Add other checks as needed for specific values or conditions
		}
	}
	
	
}

