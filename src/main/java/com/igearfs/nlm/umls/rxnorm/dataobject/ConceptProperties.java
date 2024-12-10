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

package com.igearfs.nlm.umls.rxnorm.dataobject;

public class ConceptProperties
{
	
	private String rxcui;
	private String name;
	private String synonym;
	private String tty;
	private String language;
	private String suppress;
	private String umlscui;
	
	// Getters and Setters
	public String getRxcui()
	{
		return rxcui;
	}
	
	public void setRxcui(String rxcui)
	{
		this.rxcui = rxcui;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getSynonym()
	{
		return synonym;
	}
	
	public void setSynonym(String synonym)
	{
		this.synonym = synonym;
	}
	
	public String getTty()
	{
		return tty;
	}
	
	public void setTty(String tty)
	{
		this.tty = tty;
	}
	
	public String getLanguage()
	{
		return language;
	}
	
	public void setLanguage(String language)
	{
		this.language = language;
	}
	
	public String getSuppress()
	{
		return suppress;
	}
	
	public void setSuppress(String suppress)
	{
		this.suppress = suppress;
	}
	
	public String getUmlscui()
	{
		return umlscui;
	}
	
	public void setUmlscui(String umlscui)
	{
		this.umlscui = umlscui;
	}
}
