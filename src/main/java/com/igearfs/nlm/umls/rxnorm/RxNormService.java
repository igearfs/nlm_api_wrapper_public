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

import com.igearfs.nlm.umls.spring.service.CacheManager;
import lombok.Data;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("rxNormService")
@Data
public class RxNormService
{
	
	private static final Logger logger = LoggerFactory.getLogger(RxNormService.class);
	
	// Property for default response format
	private final String defaultFormat;
	
	// cacheService service instance
	private final CacheManager cacheManager;
	
	public RxNormService(@Qualifier("redisService") CacheManager cacheManager)
			throws IOException
	{
		this.defaultFormat = "json"; // Default response format
		this.cacheManager = cacheManager;
	}
	
	/**
	 * Calls the filterByProperty API.
	 *
	 * @param rxcui      The RxCUI of the concept to filter. (Required)
	 * @param propName   The property name to filter by. (Required)
	 * @param propValues The property values to filter by. (Optional)
	 * @return The API response as a JSON string, or null if the request fails.
	 * @throws IllegalArgumentException if required parameters are missing.
	 */
	public String filterByProperty(String rxcui, String propName, String propValues, String format)
	{
		// Validate required parameters
		if (rxcui == null || rxcui.isEmpty())
		{
			throw new IllegalArgumentException("RxCUI is required.");
		}
		if (propName == null || propName.isEmpty())
		{
			throw new IllegalArgumentException("Property name is required.");
		}
		
		// Use default format if the provided format is null or empty
		if (format == null || format.isEmpty())
		{
			format = defaultFormat;
		}
		
		// Construct cache key based on input parameters
		String cacheKey = String.format("filterByProperty:%s:%s:%s", rxcui, propName, propValues);
		String cachedResponse = cacheManager.getValueFromKeyResponse(cacheKey, "rxnorm_cache");
		
		if (cachedResponse != null)
		{
			return cachedResponse; // Return cached response if available
		}
		
		String apiResponse = filterByPropertyRequest(rxcui, propName, propValues, format);
		
		if (apiResponse != null)
		{
			cacheManager.cacheResponse("rxnorm_cache", cacheKey, apiResponse, 86400); // Cache for 24 hours
		}
		
		return apiResponse;
	}
	
	/**
	 * Makes the HTTP request for filterByProperty.
	 */
	private String filterByPropertyRequest(String rxcui, String propName, String propValues, String format)
	{
		String url = String.format("https://rxnav.nlm.nih.gov/REST/rxcui/%s/filter.%s?propName=%s&propValues=%s",
				rxcui, format, propName, propValues);
		logger.info("method--> filterByPropertyRequest::", url);
		return makeHttpRequest(url);
	}
	
	/**
	 * Calls the findRelatedNDCs API to retrieve National Drug Codes (NDCs)
	 * related by a specified relationship type.
	 *
	 * @param ndc       The National Drug Code (NDC) to search for. Must not be null or empty.
	 * @param relation  The relationship type to filter the related NDCs. Must not be null or empty.
	 * @param ndcStatus (Optional) Status filter for NDCs to retrieve. Can be null or empty.
	 * @param format    (Optional) The desired response format (e.g., "json"). Defaults to "json" if null or empty.
	 * @return A JSON string response from the API by default, or null if an error occurs.
	 * @throws IllegalArgumentException if ndc or relation is null or empty.
	 */
	public String findRelatedNDCs(String ndc, String relation, String ndcStatus, String format)
	{
		// Validate required parameters
		if (ndc == null || ndc.isEmpty())
		{
			throw new IllegalArgumentException("NDC must not be null or empty.");
		}
		if (relation == null || relation.isEmpty())
		{
			throw new IllegalArgumentException("Relation must not be null or empty.");
		}
		
		// Use default format if the provided format is null or empty
		if (format == null || format.isEmpty())
		{
			format = defaultFormat;
		}
		String cacheKey = "";
		
		cacheKey = String.format("findRelatedNDCs:%s:%s:%s:%s", ndc, relation, ndcStatus, format);
		String cachedResponse = cacheManager.getValueFromKeyResponse(cacheKey, "rxnorm_cache");
		
		if (cachedResponse != null)
		{
			return cachedResponse; // Return cached response if available
		}
		
		
		String apiResponse = findRelatedNDCsRequest(ndc, relation, ndcStatus, format);
		
		if (apiResponse != null)
		{
			cacheManager.cacheResponse("rxnorm_cache", cacheKey, apiResponse, 86400); // Cache for 24 hours
		}
		
		return apiResponse;
	}
	
	
	/**
	 * Makes the HTTP request for findRelatedNDCs.
	 */
	private String findRelatedNDCsRequest(String ndc, String relation, String ndcStatus, String format)
	{
		String url = String.format("https://rxnav.nlm.nih.gov/REST/relatedndc.%s?ndc=%s&relation=%s&ndcstatus=%s",
				format, ndc, relation, ndcStatus);
		logger.info("URL: {}", url);
		return makeHttpRequest(url);
	}
	
	/**
	 * Generic method for making HTTP GET requests.
	 */
	private String makeHttpRequest(String url)
	{
		try (CloseableHttpClient httpClient = HttpClients.createDefault())
		{
			HttpGet request = new HttpGet(url);
			try (CloseableHttpResponse response = httpClient.execute(request))
			{
				if (response.getStatusLine().getStatusCode() == 200)
				{
					return EntityUtils.toString(response.getEntity());
				}
				else
				{
					logger.error("Error: Received HTTP {}", response.getStatusLine().getStatusCode());
				}
			}
		}
		catch (IOException e)
		{
			logger.error("IOException occurred: {}", e.getMessage(), e);
		}
		return null; // Return null if there was an error
	}
	
	public void close()
	{
		cacheManager.shutdown();
	}
}
