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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component("redisCacheUtil")
public class RedisCacheUtil
{
	
	private final RedisTemplate<String, Object> redisTemplate;

	@Autowired
	public RedisCacheUtil(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
    }

	public void cacheResponse(String tableName, String key, String value, int ttl) {
		// Use Redis if available
		if (redisTemplate != null) {
			redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
		}

	}

	public String getValueFromKeyResponse(String key, String tableName) {
		if (redisTemplate != null) {
			Object cachedValue = redisTemplate.opsForValue().get(key);
			return cachedValue != null ? cachedValue.toString() : null;
		}
		return null; // Return null if Redis is not available
	}

	public void updateResponse(String tableName, String key, String newValue, int ttl) {
		if (redisTemplate != null) {
			redisTemplate.opsForValue().set(key, newValue, ttl, TimeUnit.SECONDS);
		}
	}

	public void deleteResponse(String key) {
		if (redisTemplate != null) {
			redisTemplate.delete(key);
		}
	}

	public void shutdown() {

	}
}
