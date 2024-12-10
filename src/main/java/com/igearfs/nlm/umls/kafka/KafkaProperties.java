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

package com.igearfs.nlm.umls.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties
{
	private String bootstrapServers;
	private String keySerializer;
	private String valueSerializer;
	private String schemaRegistryUrl;
	private List<String> topics;
	
	// Getters and Setters
	public String getBootstrapServers()
	{
		return bootstrapServers;
	}
	
	public void setBootstrapServers(String bootstrapServers)
	{
		this.bootstrapServers = bootstrapServers;
	}
	
	public String getKeySerializer()
	{
		return keySerializer;
	}
	
	public void setKeySerializer(String keySerializer)
	{
		this.keySerializer = keySerializer;
	}
	
	public String getValueSerializer()
	{
		return valueSerializer;
	}
	
	public void setValueSerializer(String valueSerializer)
	{
		this.valueSerializer = valueSerializer;
	}
	
	public String getSchemaRegistryUrl()
	{
		return schemaRegistryUrl;
	}
	
	public void setSchemaRegistryUrl(String schemaRegistryUrl)
	{
		this.schemaRegistryUrl = schemaRegistryUrl;
	}
	
	public List<String> getTopics()
	{
		return topics;
	}
	
	public void setTopics(List<String> topics)
	{
		this.topics = topics;
	}
}
