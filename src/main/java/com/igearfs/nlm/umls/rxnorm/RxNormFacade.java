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

package com.igearfs.nlm.umls.rxnorm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.igearfs.nlm.umls.rxnorm.dataobject.FilterByPropertyResponse;
import com.igearfs.nlm.umls.rxnorm.dataobject.RelatedNDCResponse;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class RxNormFacade
{
	
	private final static String FORMAT = "json";
	private final RxNormService rxNormService;
	private final ObjectMapper objectMapper;
	
	public RxNormFacade(RxNormService rxNormService)
	{
		this.rxNormService = rxNormService;
		this.objectMapper = new ObjectMapper();
	}
	
	
	public RelatedNDCResponse findRelatedNDCs(String ndc, String relation, String ndcStatus)
	{
		
		try
		{
			// Call the service to get JSON response
			String jsonResponse = rxNormService.findRelatedNDCs(ndc, relation, ndcStatus, FORMAT);
			
			// Parse the JSON response into a RelatedNDCResponse object
			if (jsonResponse != null)
			{
				return objectMapper.readValue(jsonResponse, RelatedNDCResponse.class);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public FilterByPropertyResponse filterByProperty(String rxcui, String propName, String propValues)
	{
		try
		{
			// Call the service to get JSON response
			String jsonResponse = rxNormService.filterByProperty(rxcui, propName, propValues, FORMAT);
			
			// Parse the JSON response into a FilterByPropertyResponse object
			if (jsonResponse != null)
			{
				return objectMapper.readValue(jsonResponse, FilterByPropertyResponse.class);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
}
