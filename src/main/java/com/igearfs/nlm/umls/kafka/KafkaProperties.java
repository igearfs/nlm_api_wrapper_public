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
