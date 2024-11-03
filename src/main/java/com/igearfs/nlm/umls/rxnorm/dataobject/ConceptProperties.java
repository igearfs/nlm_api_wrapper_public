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
