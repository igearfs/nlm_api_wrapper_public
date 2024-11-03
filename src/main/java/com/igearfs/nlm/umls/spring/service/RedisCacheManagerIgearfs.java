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
