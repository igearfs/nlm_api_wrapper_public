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

package com.igearfs.nlm.umls.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil
{
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * Converts a JSON string to a Java object of the specified type.
	 *
	 * @param json  The JSON string.
	 * @param clazz The class to convert to.
	 * @param <T>   The type of the response object.
	 * @return An instance of the response object.
	 * @throws IOException If an error occurs during conversion.
	 */
	public static <T> T convertJsonToObject(String json, Class<T> clazz)
			throws IOException
	{
		return objectMapper.readValue(json, clazz);
	}
}
