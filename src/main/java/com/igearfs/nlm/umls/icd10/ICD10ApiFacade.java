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

package com.igearfs.nlm.umls.icd10;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.f4b6a3.uuid.UuidCreator;
import com.igearfs.nlm.umls.icd10.dataobject.ICD10Code;
import com.igearfs.nlm.umls.icd10.dataobject.ICD10SearchResponse;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Facade class for interacting with the ICD-10-CM API through the {@link ICD10ApiService}.
 * This class abstracts the complexities of interacting with the API and processes the
 * returned data into a more user-friendly structure.
 *
 * <p>API Documentation:
 * <a href="https://clinicaltables.nlm.nih.gov/api/icd10cm/v3/search">ICD-10-CM API Search Documentation</a></p>
 *
 * <p>Default values for parameters:</p>
 * <ul>
 *     <li><b>terms</b>: The search term is required. It's the word or partial term to search for.</li>
 *     <li><b>maxList</b>: The maximum number of results to retrieve. Default is 7. Maximum allowed is 500.</li>
 *     <li><b>count</b>: The number of results per page (for pagination). Default is 7. Maximum allowed is 500.</li>
 *     <li><b>offset</b>: The starting result number (0-based) for pagination. Default is 0.</li>
 *     <li><b>df</b>: The display fields to return (for showing to the user). Default is an empty string, meaning no
 *     fields.</li>
 *     <li><b>sf</b>: The fields to search within (search fields). Default is "code,name".</li>
 *     <li><b>cf</b>: The code field to use for the returned items. Default is an empty string.</li>
 *     <li><b>q</b>: Additional query string for narrowing results. Optional and can include field names or wildcards
 *     .</li>
 * </ul>
 *
 * <p>The API will return the following structure:</p>
 * <ul>
 *     <li>Total number of results available on the server.</li>
 *     <li>An array of codes (specified by the <b>cf</b> parameter).</li>
 *     <li>An array of display strings for each returned item (specified by the <b>df</b> parameter).</li>
 *     <li>A hash of "extra" data fields (specified by the <b>ef</b> parameter).</li>
 *     <li>The code system used (only in code-system aware APIs).</li>
 * </ul>
 *
 * <p>For more details, refer to the API documentation link above.</p>
 */

@Data
@Service
public class ICD10ApiFacade
{
	
	private static final Logger logger = LoggerFactory.getLogger(ICD10ApiFacade.class);
	
	// Parameters for search
	private String terms;   // Required search term or partial word
	private Integer maxList = 7;   // Maximum number of results to retrieve (Default is 7, max is 500)
	private Integer count = 7;     // Number of results to retrieve per page (pagination, default 7)
	private Integer offset = 0;    // Starting result number for pagination (default 0)
	private String df = "";        // Display fields (default is empty string, meaning no fields)
	private String sf = "code,name";   // Search fields (default is "code,name")
	private String cf = "";        // Code field (default is empty string, meaning no code field)
	private String q;              // Optional additional query string
	
	@Autowired
	private ICD10ApiService apiService;
	
	public static void main(String[] args)
	{
		System.out.println("WTF");
	}
	
	public ICD10SearchResponse searchICD10()
			throws IOException
	{
		// Generate a UUIDv4 if uniqueId is not provided
		String uniqueId = UuidCreator.getRandomBased().toString();  // Generates a random UUID (UUIDv4)
		String source = "unknown";  // Default source if not provided
		
		return this.searchICD10(uniqueId, source);
	}
	
	/**
	 * NOT YET FOR PUBLiC USE!!!! Uses default params that are not implemented yet.
	 * Refactoring code.
	 *
	 * Performs a search for ICD-10 codes based on the currently set parameters.
	 * Optionally accepts a unique ID and source for future Kafka analytics use.
	 * <p>
	 * If the `uniqueId` is not provided (null), a random UUIDv4 will be generated.
	 * If the `source` is not provided (null), it will default to "unknown".
	 *
	 * @param uniqueId Optional unique identifier for tracking the request. If not provided, a random UUIDv4 will be
	 *                    generated.
	 * @param source   Optional source of the request for analytics purposes. If not provided, defaults to "unknown".
	 * @return An {@link ICD10SearchResponse} object containing the parsed result.
	 * @throws IOException If an error occurs during the API request.
	 */
	private ICD10SearchResponse searchICD10(String uniqueId, String source)
			throws IOException
	{
		
		// Validate required parameters
		if (terms == null || terms.isEmpty())
		{
			throw new IllegalArgumentException("Search terms must not be null or empty.");
		}
		
		// Set parameters in the underlying service
		apiService.setTerms(terms);
		apiService.setMaxList(maxList);
		apiService.setCount(count);
		apiService.setOffset(offset);
		apiService.setDf(df);
		apiService.setSf(sf);
		apiService.setCf(cf);
		if (q != null)
		{
			apiService.setQ(q);
		}
		
		// Log the search request details
		logger.info("Searching ICD-10 with terms: {}, df: '{}', sf: '{}', cf: '{}'", terms, df, sf, cf);
		
		// Perform the search using the service
		String jsonResponse = apiService.searchICD10();
		
		// Parse and return the result
		return parseSearchResponse(jsonResponse);
	}
	
	/**
	 * Parses the raw JSON response from the ICD-10 API into an {@link ICD10SearchResponse} object.
	 *
	 * @param jsonResponse The raw JSON response from the API.
	 * @return A structured {@link ICD10SearchResponse} object.
	 */
	private ICD10SearchResponse parseSearchResponse(String jsonResponse)
	{
		try
		{
			ObjectMapper objectMapper = new ObjectMapper();
			List<Object> rawResponse = objectMapper.readValue(jsonResponse, List.class);
			
			Integer totalResults = (Integer) rawResponse.get(0);
			List<String> codes = (List<String>) rawResponse.get(1);
			Map<String, List<String>> extraData = (Map<String, List<String>>) rawResponse.get(2);
			List<List<String>> displayStrings = (List<List<String>>) rawResponse.get(3);
			
			// Convert display strings to ICD10Code objects
			List<ICD10Code> icd10Codes = new ArrayList<>();
			for (int i = 0; i < codes.size(); i++)
			{
				String code = codes.get(i);
				String description = displayStrings.get(i).get(1); // Get the description from the display strings
				icd10Codes.add(new ICD10Code(code, description));
			}
			
			logger.info("Total results: {}", totalResults);
			logger.info("Codes: {}", icd10Codes);
			
			return new ICD10SearchResponse(totalResults, icd10Codes, extraData);
		}
		catch (IOException e)
		{
			logger.error("Failed to parse search response: {}", e.getMessage());
			throw new RuntimeException("Failed to parse ICD-10 API response", e);
		}
	}
	
	
	/**
	 * Allows setting multiple parameters at once, useful for complex queries.
	 *
	 * @param terms   The search term or partial word to query.
	 * @param maxList The maximum number of results to return.
	 * @param count   The number of results per page for pagination.
	 * @param offset  The starting result number for pagination.
	 * @param df      The display fields to return (default is empty string).
	 * @param sf      The fields to search within (default is "code,name").
	 * @param cf      The field to use as the "code" for returned data (default is empty string).
	 * @param q       Additional query string for filtering results.
	 */
	public void setSearchParameters(String terms, Integer maxList, Integer count, Integer offset, String df, String sf
			, String cf, String q)
	{
		this.terms = terms;
		this.maxList = maxList != null ? maxList : this.maxList;
		this.count = count != null ? count : this.count;
		this.offset = offset != null ? offset : this.offset;
		this.df = df != null ? df : this.df;
		this.sf = sf != null ? sf : this.sf;
		this.cf = cf != null ? cf : this.cf;
		this.q = q;
	}
	
	/**
	 * Allows setting multiple parameters at once, useful for complex queries.
	 *
	 * @param terms The search term or partial word to query. (required)
	 */
	public void setDefaultSearchParameters(String terms)
	{
		this.terms = terms;
	}
}

