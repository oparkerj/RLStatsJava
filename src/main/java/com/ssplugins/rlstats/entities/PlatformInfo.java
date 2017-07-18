package com.ssplugins.rlstats.entities;

import com.ssplugins.rlstats.util.NameIDPair;
import org.json.JSONObject;

/**
 * Stores a platform result from the API.
 */
public class PlatformInfo extends NameIDPair {
	
	private JSONObject raw;
	
	public PlatformInfo(JSONObject object) {
		this(object.getString("name"), object.getInt("id"));
		raw = object;
	}
	
	private PlatformInfo(String name, int id) {
		super(name, id);
	}
	
	/**
	 * Get the raw {@link JSONObject} used to create the object.
	 * @return Raw {@link JSONObject}.
	 */
	public JSONObject raw() {
		return raw;
	}
	
	/**
	 * Get this platform in the form of a {@link Platform}.
	 * @return Platform object or null if not found.
	 * @see Platform#fromId(int)
	 */
	public Platform getPlatform() {
		return Platform.fromId(getId());
	}
	
}
