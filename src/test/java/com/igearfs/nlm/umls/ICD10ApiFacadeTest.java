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


import com.igearfs.nlm.umls.icd10.ICD10ApiFacade;
import com.igearfs.nlm.umls.icd10.dataobject.ICD10SearchResponse;
import com.igearfs.nlm.umls.spring.AppConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class ICD10ApiFacadeTest
{
	
	@Autowired
	private ICD10ApiFacade icd10ApiFacade;
	
	
	@Test
	void testSearchICD10Successful()
			throws IOException
	{
		// Arrange
		icd10ApiFacade.setSearchParameters("tuberc", 7, 7, 0, "", "code,name", "code", null);
		
		// Act
		ICD10SearchResponse response = icd10ApiFacade.searchICD10();
		
		// Assert
		assertNotNull(response);
		assertEquals(78, response.getTotalResults());
		assertFalse(response.getCodes().isEmpty());
	}
	
	@Test
	void testSearchICD10WithEmptyTerms()
	{
		// Arrange
		icd10ApiFacade.setSearchParameters("", 7, 7, 0, "", "code,name", "code", null);
		
		// Act & Assert
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, icd10ApiFacade::searchICD10);
		assertEquals("Search terms must not be null or empty.", exception.getMessage());
	}
	
	@Test
	void testSearchICD10ParsingError()
			throws IOException
	{
		// Set up an invalid response manually
		icd10ApiFacade.setSearchParameters("", 7, 7, 0, "a_fdadsasdf", "code,name", "code", null);

		// Act & Assert
		RuntimeException exception = assertThrows(IllegalArgumentException.class, icd10ApiFacade::searchICD10);
		assertEquals("Search terms must not be null or empty.", exception.getMessage());
	}
	
	
	@Test
	void testSetSearchParameters()
	{
		// Act
		icd10ApiFacade.setSearchParameters("test", 10, 5, 1, "code", "name", "code", "additional query");
		
		// Assert
		assertEquals("test", icd10ApiFacade.getTerms());
		assertEquals(10, icd10ApiFacade.getMaxList());
		assertEquals(5, icd10ApiFacade.getCount());
		assertEquals(1, icd10ApiFacade.getOffset());
		assertEquals("code", icd10ApiFacade.getDf());
		assertEquals("name", icd10ApiFacade.getSf());
		assertEquals("code", icd10ApiFacade.getCf());
		assertEquals("additional query", icd10ApiFacade.getQ());
	}
	
	@Test
	void testSetDefaultSearchParameters()
			throws IOException
	{
		// Act
		icd10ApiFacade.setDefaultSearchParameters("test");
		
		// Assert
		assertEquals("test", icd10ApiFacade.getTerms());
		
		// Arrange
		icd10ApiFacade.setDefaultSearchParameters("tuberc");
		
		// Act
		ICD10SearchResponse response = icd10ApiFacade.searchICD10();
		
		// Assert
		assertNotNull(response);
		assertEquals(78, response.getTotalResults());
		assertFalse(response.getCodes().isEmpty());
	}
}
