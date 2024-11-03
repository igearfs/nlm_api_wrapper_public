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
 *
 */

package com.igearfs.nlm.umls.spring;

import com.igearfs.nlm.umls.icd10.ICD10ApiFacade;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
@ComponentScan(basePackages = "com.igearfs.nlm.umls.*")
public class AppConfig
{
	
	@Autowired
	private Environment env;
	
	@PostConstruct
	public void init()
	{
		try
		{
			// Load YAML properties
			YamlPropertySourceLoader yamlLoader = new YamlPropertySourceLoader();
			Resource resource = new ClassPathResource("application.yml");
			List<PropertySource<?>> yamlPropertySources = yamlLoader.load("application.yml", resource);
//			System.out.println("*********************** YML LOADED HERE!!!");
			// Add each property source individually
			for (PropertySource<?> propertySource : yamlPropertySources)
			{
				((ConfigurableEnvironment) env).getPropertySources().addLast(propertySource);
			}
//			printAllProperties();

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	private void printAllProperties()
	{
		System.out.println("*********************** ALL PROPERTIES LOADED ***********************");
		for (PropertySource<?> propertySource : ((ConfigurableEnvironment) env).getPropertySources())
		{
			if (propertySource.getSource() instanceof java.util.Map)
			{
				java.util.Map<String, Object> map = (java.util.Map<String, Object>) propertySource.getSource();
				for (String propertyName : map.keySet())
				{
					System.out.println(propertyName + ": " + env.getProperty(propertyName));
				}
			}
		}
		System.out.println("**********************************************************************");
	}
	
	
	@Bean
	public ICD10ApiFacade icd10ApiFacade()
	{
		return new ICD10ApiFacade();
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory()
	{
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(
				env.getProperty("spring.redis.host"),
				Integer.parseInt(env.getProperty("spring.redis.port"))
		);
		
		JedisConnectionFactory factory = new JedisConnectionFactory(config);

		return factory;
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory)
	{
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}

}
