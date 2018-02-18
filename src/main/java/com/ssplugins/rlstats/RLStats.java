package com.ssplugins.rlstats;

import com.mashape.unirest.http.Unirest;

public final class RLStats {
	
	/**
	 * Base URL for API requests.
	 */
	public static final String BASE_URL = "https://api.rocketleaguestats.com/";
	
	/**
	 * GitHub link for library.
	 */
	public static final String GITHUB = "https://github.com/567legodude/RLStatsJava";
	
	/**
	 * Current version of the library.
	 */
	public static final String VERSION = "1.1.0";
	
	/**
	 * User agent used for making requests.
	 */
	public static final String USER_AGENT = "RLStatsAPI (" + VERSION + ", " + GITHUB + ")";
	
	/**
	 * Connection timeout used for requests.
	 */
	public static long CONNECTION_TIMEOUT = 10000L;
	
	/**
	 * Socket timeout used for request.
	 */
	public static long SOCKET_TIMEOUT = 60000L;
	
	static {
		Unirest.setDefaultHeader("User-Agent", USER_AGENT);
	}
	
	private RLStats() {}
	
	/**
	 * Get an instance of the API.
	 * @return New instance of the API.
	 * @see #getAPI(String)
	 */
	public static RLStatsAPI getAPI() {
		return new IRLStatsAPI();
	}
	
	/**
	 * Get an instance of the API.
	 * @param apiKey Auth key to use for requests.
	 * @return New instance of the API.
	 * @see RLStatsAPI#setAuthKey(String)
	 */
	public static RLStatsAPI getAPI(String apiKey) {
		IRLStatsAPI api = (IRLStatsAPI) getAPI();
		api.setAuthKey(apiKey);
		return api;
	}

}
