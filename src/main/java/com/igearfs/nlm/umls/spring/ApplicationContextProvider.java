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

package com.igearfs.nlm.umls.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextProvider
{
	
	private static ApplicationContext context;
	
	// Static method to load the application context
	public static void loadApplicationContext()
	{
		if (context == null)
		{
			context = new AnnotationConfigApplicationContext(AppConfig.class);
		}
	}
	
	// Static method to retrieve a bean from the context
	public static <T> T getBean(Class<T> clazz)
	{
		if (context == null)
		{
			throw new IllegalStateException("Application context not initialized. Call loadApplicationContext() first" +
					".");
		}
		return context.getBean(clazz);
	}
}
