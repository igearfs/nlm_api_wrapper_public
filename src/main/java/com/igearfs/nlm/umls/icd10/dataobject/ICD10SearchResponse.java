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

package com.igearfs.nlm.umls.icd10.dataobject;

import lombok.Data;
import org.apache.avro.reflect.AvroSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A structured response object for the ICD-10 search results.
 * This class holds the data returned from the API and provides utility methods for accessing it.
 */
@AvroSchema("{\"type\": \"record\", \"name\": \"ICD10SearchResponse\", \"fields\": [" +
		"{\"name\": \"totalResults\", \"type\": \"int\"}," +
		"{\"name\": \"codes\", \"type\": {\"type\": \"array\", \"items\": \"ICD10Code\"}}," +
		"{\"name\": \"extraData\", \"type\": {\"type\": \"map\", \"values\": {\"type\": \"array\", \"items\": " +
		"\"string\"}}}]}")
@Data
public class ICD10SearchResponse
{
	
	private static final Logger logger = LoggerFactory.getLogger(ICD10SearchResponse.class);
	
	/**
	 * The total number of results available on the server.
	 * This number might be greater than the number of results returned.
	 */
	private Integer totalResults;
	
	/**
	 * A list of ICD-10 codes returned in the search.
	 * Each entry is an ICD10Code object that contains both the code and its description.
	 */
	private List<ICD10Code> codes;
	
	/**
	 * Extra data fields, if any, returned from the search (as requested by the ef parameter).
	 * The map contains field names as keys and the corresponding data arrays as values.
	 */
	private Map<String, List<String>> extraData;
	
	/**
	 * Constructor for ICD10SearchResponse.
	 *
	 * @param totalResults The total number of results available on the server.
	 * @param codes        A list of ICD10Code objects returned by the API.
	 * @param extraData    Any additional data returned by the API.
	 */
	public ICD10SearchResponse(Integer totalResults, List<ICD10Code> codes, Map<String, List<String>> extraData)
	{
		this.totalResults = totalResults;
		this.codes = codes;
		this.extraData = extraData;
	}
	
	/**
	 * Retrieves the description for a given ICD-10 code.
	 *
	 * @param code The ICD-10 code to look up.
	 * @return An Optional containing the description of the code, or an empty Optional if not found.
	 */
	public Optional<String> getDescriptionForCode(String code)
	{
		return codes.stream()
				.filter(icd10Code -> icd10Code.getCode().equals(code))
				.map(ICD10Code::getDescription)
				.findFirst();
	}
	
	/**
	 * Checks if a given code is present in the search results.
	 *
	 * @param code The ICD-10 code to search for.
	 * @return True if the code is present in the results, false otherwise.
	 */
	public boolean containsCode(String code)
	{
		return codes.stream().anyMatch(icd10Code -> icd10Code.getCode().equals(code));
	}
	
	/**
	 * Retrieves extra data for a given field.
	 *
	 * @param field The field name as specified in the 'ef' parameter of the search.
	 * @return An Optional containing the list of data for the field, or an empty Optional if the field does not exist.
	 */
	public Optional<List<String>> getExtraDataForField(String field)
	{
		if (extraData != null && extraData.containsKey(field))
		{
			return Optional.of(extraData.get(field));
		}
		
		logger.warn("Field '{}' not found in the extra data.", field);
		return Optional.empty();
	}
	
	/**
	 * Retrieves all the codes along with their descriptions as a map.
	 *
	 * @return A map where each key is a code and the value is the description for that code.
	 */
	public Map<String, String> getAllCodesWithDescriptions()
	{
		return codes.stream()
				.collect(Collectors.toMap(
						ICD10Code::getCode,
						ICD10Code::getDescription
				));
	}
	
	/**
	 * Logs the entire response in a structured format for debugging or audit purposes.
	 */
	public void logResponseDetails()
	{
		logger.info("ICD-10 Search Response: Total Results = {}", totalResults);
		logger.info("Codes and Descriptions:");
		for (ICD10Code code : codes)
		{
			logger.info("Code: {}, Description: {}", code.getCode(), code.getDescription());
		}
		
		if (extraData != null && !extraData.isEmpty())
		{
			logger.info("Extra Data:");
			extraData.forEach((field, values) ->
			{
				logger.info("Field: {}, Values: {}", field, values);
			});
		}
	}
}
