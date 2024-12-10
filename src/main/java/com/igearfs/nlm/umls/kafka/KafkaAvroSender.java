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

import com.igearfs.nlm.umls.icd10.ICD10KafkaResponseWrapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

//@Component
public class KafkaAvroSender
{
	private KafkaProducer<String, ICD10KafkaResponseWrapper> producer;
	
	//@Autowired
	private KafkaProperties kafkaProperties;
	
	@Getter
	@Setter
	private String topic;
	
	public KafkaAvroSender()
	{
		this.topic = "default-topic"; // You can set a default or get from KafkaProperties
	}
	
	
	@PostConstruct
	public void init()
	{
		Properties props = new Properties();
		props.put("bootstrap.servers", kafkaProperties.getBootstrapServers());
		props.put("key.serializer", kafkaProperties.getKeySerializer());
		props.put("value.serializer", kafkaProperties.getValueSerializer());
		props.put("schema.registry.url", kafkaProperties.getSchemaRegistryUrl());
		this.producer = new KafkaProducer<>(props);
	}
	
	public void sendAvroMessage(ICD10KafkaResponseWrapper responseWrapper)
	{
		ProducerRecord<String, ICD10KafkaResponseWrapper> producerRecord = new ProducerRecord<>(topic,
				responseWrapper);
		producer.send(producerRecord, new Callback()
		{
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception)
			{
				if (exception != null)
				{
					exception.printStackTrace();
				}
				else
				{
					System.out.println("Message sent to topic " + metadata.topic() + " partition " + metadata.partition());
				}
			}
		});
	}
	
	@PreDestroy
	public void close()
	{
		if (producer != null)
		{
			producer.close();
		}
	}
}
