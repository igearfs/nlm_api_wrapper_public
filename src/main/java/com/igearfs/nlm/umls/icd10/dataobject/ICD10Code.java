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

package com.igearfs.nlm.umls.icd10.dataobject;


import lombok.Data;
import org.apache.avro.reflect.AvroSchema;

import java.time.Instant;

@AvroSchema("{\"type\": \"record\", \"name\": \"ICD10Code\", \"fields\": [" +
		"{\"name\": \"code\", \"type\": \"string\"}," +
		"{\"name\": \"description\", \"type\": \"string\"}]}")
@Data
public class ICD10Code
{
	private String code;
	private String description;
	private Instant insertDate;
	private Instant updateDate;
	private String uuid;

	public ICD10Code(){}
	public ICD10Code(String code, String description)
	{
		this.code = code;
		this.description = description;
	}

	public ICD10Code(String guid, String key, String value, Instant insertDate, Instant updateDate) {

		this.uuid = guid;
		this.code = key;
		this.description = value;
		this.insertDate = insertDate;
		this.updateDate = updateDate;

	}
}
