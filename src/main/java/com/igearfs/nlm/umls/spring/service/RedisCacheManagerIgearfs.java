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

package com.igearfs.nlm.umls.spring.service;

import com.igearfs.nlm.umls.util.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("redisService")
public class RedisCacheManagerIgearfs implements CacheManager
{
	
	@Autowired
	private final RedisCacheUtil redisCacheUtil;
	
	@Autowired
	public RedisCacheManagerIgearfs(RedisCacheUtil redisCacheUtil)
	{
		this.redisCacheUtil = redisCacheUtil;
	}
	
	public void cacheResponse(String tableName, String key, String value, int ttl)
	{
		redisCacheUtil.cacheResponse(tableName, key, value, ttl);
	}
	
	public String getValueFromKeyResponse(String key, String tableName)
	{
		return redisCacheUtil.getValueFromKeyResponse(key, tableName);
	}

	public void shutdown()
	{
		redisCacheUtil.shutdown();
	}
	
}
