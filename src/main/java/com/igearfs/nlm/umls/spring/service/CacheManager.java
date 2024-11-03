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

package com.igearfs.nlm.umls.spring.service;

public interface CacheManager
{
	
	void cacheResponse(String tableName, String key, String value, int ttl);

	/**
	 *
	 * @param key the key to find the value from the cassandra table.
	 * @param tableName this is for cassandra to find the value for the key.
	 * @return
	 */
	String getValueFromKeyResponse(String key, String tableName);

	void shutdown();
}
