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
