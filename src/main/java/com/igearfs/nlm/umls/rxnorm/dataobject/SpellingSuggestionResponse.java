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

public class SpellingSuggestionResponse
{
	private String suggestion;
	
	public String getSuggestion()
	{
		return suggestion;
	}
	
	public void setSuggestion(String suggestion)
	{
		this.suggestion = suggestion;
	}
}
    