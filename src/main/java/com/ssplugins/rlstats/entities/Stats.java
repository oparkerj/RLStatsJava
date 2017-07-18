package com.ssplugins.rlstats.entities;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores the stats for a player.
 */
public class Stats {
	
	private JSONObject raw;
	private Map<Stat, Integer> map = new HashMap<>();
	
	public Stats(JSONObject object) {
		raw = object;
		for (Stat stat : Stat.values()) {
			String s = stat.getQueryName();
			if (object.has(s)) {
				map.put(Stat.fromName(s), object.getInt(s));
			}
		}
	}
	
	/**
	 * Get the raw {@link JSONObject} used to create the object.
	 * @return Raw {@link JSONObject}.
	 */
	public JSONObject raw() {
		return raw;
	}
	
	/**
	 * Get all the stats for the player.
	 * @return Map of all the player's stats.
	 */
	public Map<Stat, Integer> getAllStats() {
		return Collections.unmodifiableMap(map);
	}
	
	/**
	 * Get a specific stat of the player.
	 * @param stat Which stat to get.
	 * @return The value of the stat for the player.
	 */
	public int getStat(Stat stat) {
		return map.get(stat);
	}
	
}
