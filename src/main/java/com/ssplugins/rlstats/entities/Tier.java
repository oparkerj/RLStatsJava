package com.ssplugins.rlstats.entities;

import com.ssplugins.rlstats.util.NameIDPair;
import org.json.JSONObject;

public class Tier extends NameIDPair {
	
	private JSONObject raw;
	
	public Tier(JSONObject object) {
		this(object.getString("tierName"), object.getInt("tierId"));
		raw = object;
	}
	
	public Tier(String name, int id) {
		super(name, id);
	}
	
	public JSONObject raw() {
		return raw;
	}
	
}
