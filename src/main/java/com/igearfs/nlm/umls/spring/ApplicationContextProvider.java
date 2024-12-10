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
