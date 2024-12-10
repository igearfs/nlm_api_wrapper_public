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
import com.igearfs.nlm.umls.icd10.dataobject.ICD10Code;
import com.igearfs.nlm.umls.icd10.dataobject.ICD10SearchResponse;

import java.util.List;
import java.util.Map;

public class Main
{
	public static void main(String[] args)
	{
		KafkaAvroSender sender = new KafkaAvroSender();
		sender.setTopic("your-topic");
		
		ICD10Code code1 = new ICD10Code("A00", "Cholera");
		ICD10Code code2 = new ICD10Code("A01", "Typhoid and paratyphoid fevers");
		
		ICD10SearchResponse response = new ICD10SearchResponse(2, List.of(code1, code2), Map.of());
		ICD10KafkaResponseWrapper wrapper = new ICD10KafkaResponseWrapper();
		wrapper.setMessageId("SSS");
		wrapper.setIcd10SearchResponse(response);
		try
		{
			sender.sendAvroMessage(wrapper);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			sender.close();
		}
	}
}
