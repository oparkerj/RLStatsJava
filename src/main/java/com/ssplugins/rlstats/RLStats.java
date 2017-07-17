package com.ssplugins.rlstats;

import com.mashape.unirest.http.Unirest;

public final class RLStats {
	
	public static final String BASE_URL = "";
	
	public static final String GITHUB = "";
	
	public static final String VERSION = "1.0.0";
	
	public static final String USER_AGENT = "RLStatsAPI (" + VERSION + ", " + GITHUB + ")";
	
	static {
		Unirest.setDefaultHeader("User-Agent", USER_AGENT);
	}
	
	private RLStats() {}
	
	public static RLStatsAPI getAPI() {
	
	}

}
