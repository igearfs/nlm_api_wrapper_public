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
import com.igearfs.nlm.umls.rxnorm.dataobject.RxNormResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RxNormTestClient
{
	private static final Logger logger = LoggerFactory.getLogger(RxNormTestClient.class);
	
	public static void main(String[] args)
	{
		try
		{
			// 1. Set up the RxNorm API URL
			String rxNormApiUrl = "https://rxnav.nlm.nih.gov/REST/rxcui.json?name=Lipitor+10+mg+Tab&search=10";  //
			// Example query for "aspirin"
			
			// 2. Create an HttpClient
			HttpClient client = HttpClient.newHttpClient();
			
			// 3. Create an HttpRequest
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(rxNormApiUrl))
					.GET()
					.build();

// 4. Send the request and get the response
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

// Print the response body to inspect if it's HTML
			logger.info("Raw Response: {}", response.body());
			
			// 5. Check if the response was successful (status code 200)
			if (response.statusCode() == 200)
			{
				String jsonResponse = response.body();
				
				// 6. Use Jackson ObjectMapper to map JSON to Java object
				ObjectMapper objectMapper = new ObjectMapper();
				RxNormResponse rxNormResponse = objectMapper.readValue(jsonResponse, RxNormResponse.class);
				
				// 7. Access the data for testing
				logger.info("Drug Name: " + rxNormResponse.getIdGroup().getName());
				logger.info("RXCUI: " + rxNormResponse.getIdGroup().getRxnormId());
			}
			else
			{
				logger.info("Error: Unable to fetch data. Status code: " + response.statusCode());
			}
		}
		catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
