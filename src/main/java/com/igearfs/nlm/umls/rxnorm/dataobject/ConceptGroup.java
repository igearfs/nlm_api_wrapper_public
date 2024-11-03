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

import java.util.List;

public class ConceptGroup
{
	
	private String tty;
	private List<ConceptProperties> conceptProperties;
	
	// Getters and Setters
	public String getTty()
	{
		return tty;
	}
	
	public void setTty(String tty)
	{
		this.tty = tty;
	}
	
	public List<ConceptProperties> getConceptProperties()
	{
		return conceptProperties;
	}
	
	public void setConceptProperties(List<ConceptProperties> conceptProperties)
	{
		this.conceptProperties = conceptProperties;
	}
}
