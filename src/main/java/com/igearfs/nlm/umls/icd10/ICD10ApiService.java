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


package com.igearfs.nlm.umls.icd10;

import com.igearfs.nlm.umls.spring.service.CacheManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Data
@Service
public class ICD10ApiService {

	@Value("${nlm.api.icd10.base-url}")
    private String BASE_URL;
	private final CacheManager cacheManager;
	private String terms;
	private Integer maxList = 7;
	private Integer count = 7;
	private Integer offset = 0;
	private String q;
	private String df = ""; // Default to blank
	private String sf = "code,name"; // Default to code,name
	private String cf = ""; // Default to blank

	public ICD10ApiService(@Qualifier("redisService") CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public String searchICD10() throws IOException {
		if (terms == null || terms.isEmpty()) {
			throw new IllegalArgumentException("Terms parameter is required.");
		}

		validateFieldParameters();

		String cacheKey = createCacheKey();
		String cachedResponse = cacheManager.getValueFromKeyResponse(cacheKey, "icd10_cache");

		if (cachedResponse != null) {
			return cachedResponse;
		}

		StringBuilder urlBuilder = new StringBuilder(BASE_URL);
		urlBuilder.append("?terms=").append(terms)
				.append("&maxList=").append(maxList)
				.append("&count=").append(count)
				.append("&offset=").append(offset);

		if (q != null) {
			urlBuilder.append("&q=").append(q);
		}
		if (!df.isEmpty()) {
			urlBuilder.append("&df=").append(df);
		}
		if (!sf.isEmpty()) {
			urlBuilder.append("&sf=").append(sf);
		}
		if (!cf.isEmpty()) {
			urlBuilder.append("&cf=").append(cf);
		}

		log.info("Request URL: {}", urlBuilder.toString());

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpGet request = new HttpGet(urlBuilder.toString());
			HttpResponse response = httpClient.execute(request);
			String result = EntityUtils.toString(response.getEntity());

			cacheManager.cacheResponse("icd10_cache", cacheKey, result, 3600); // Cache for 1 hour
			return result;
		}
	}

	private void validateFieldParameters() {
		// Field validation code here
	}

	private String createCacheKey() {
		return "ICD10|" + terms + "|" + maxList + "|" + count + "|" + offset + "|" + (q != null ? q : "");
	}
}
