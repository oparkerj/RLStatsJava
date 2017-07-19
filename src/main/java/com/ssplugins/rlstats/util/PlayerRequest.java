package com.ssplugins.rlstats.util;

import com.ssplugins.rlstats.entities.Platform;
import org.json.JSONObject;

/**
 * Represents a request to retrieve player data.
 */
public class PlayerRequest {

	private String id;
	private int platform;
	
	/**
	 * Create player request.
	 * @param id Steam 64 ID, PSN Username, Xbox GamerTag, or XUID.
	 * @param platform Which platform to search for.
	 * @see #PlayerRequest(String, Platform)
	 */
	public PlayerRequest(String id, int platform) {
		this.id = id;
		this.platform = platform;
	}
	
	/**
	 * Create player request.
	 * @param id Steam 64 ID, PSN Username, Xbox GamerTag, or XUID.
	 * @param platform Which platform to search for.
	 */
	public PlayerRequest(String id, Platform platform) {
		this(id, platform.getId());
	}
	
	/**
	 * Get the ID for this request.
	 * @return ID of this request.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Get platform for this request.
	 * @return Platform of this request.
	 */
	public int getPlatform() {
		return platform;
	}
	
	/**
	 * Get this object in the form of a {@link JSONObject}
	 * @return The current object in {@link JSONObject} form.
	 */
	public JSONObject toJSONObject() {
		JSONObject object = new JSONObject();
		object.put("platformId", String.valueOf(platform)).put("uniqueId", id);
		return object;
	}
	
	@Override
	public String toString() {
		return toJSONObject().toString();
	}
}
