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
