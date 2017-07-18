package com.ssplugins.rlstats.entities;

import java.util.Arrays;

/**
 * Represents a player stat.
 */
public enum Stat {
	
	WINS("wins"),
	GOALS("goals"),
	MVP("mvps"),
	SAVES("saves"),
	SHOTS("shots"),
	ASSISTS("assists");
	
	private String name;
	
	Stat(String name) {
		this.name = name;
	}
	
	/**
	 * Get the name of this stat used for API requests.
	 * @return API stat name.
	 */
	public String getQueryName() {
		return name;
	}
	
	/**
	 * Get a {@link Stat} by it's query name.
	 * @param name Name of stat to get.
	 * @return Stat object.
	 */
	public static Stat fromName(String name) {
		for (Stat s : Arrays.asList(values())) {
			if (s.getQueryName().equalsIgnoreCase(name)) return s;
		}
		return WINS;
	}
	
}
