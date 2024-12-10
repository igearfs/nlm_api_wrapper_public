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

package com.igearfs.nlm.umls.icd10;

import com.igearfs.nlm.umls.icd10.dataobject.ICD10SearchResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.avro.reflect.AvroSchema;

/**
 * A wrapper class for responses to be sent to Kafka.
 */
@AvroSchema("{\"type\": \"record\", \"name\": \"ICD10KafkaResponseWrapper\", \"fields\": [" +
		"{\"name\": \"messageId\", \"type\": \"string\"}," +
		"{\"name\": \"zipCode\", \"type\": \"string\"}," +
		"{\"name\": \"ageRange\", \"type\": \"string\"}," +
		"{\"name\": \"icd10SearchResponse\", \"type\": {" +
		"\"type\": \"record\", \"name\": \"ICD10SearchResponse\", \"fields\": [" +
		"{\"name\": \"totalResults\", \"type\": \"int\"}," +
		"{\"name\": \"codes\", \"type\": {\"type\": \"array\", \"items\": \"ICD10Code\"}}," +
		"{\"name\": \"extraData\", \"type\": {\"type\": \"map\", \"values\": {\"type\": \"array\", \"items\": " +
		"\"string\"}}}]" +
		"}}" +
		"]}")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ICD10KafkaResponseWrapper
{
	private String messageId;
	private String zipCode;
	private String ageRange;
	private ICD10SearchResponse icd10SearchResponse; // The main data object
}
