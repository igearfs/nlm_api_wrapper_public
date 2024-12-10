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
