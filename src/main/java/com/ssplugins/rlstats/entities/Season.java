package com.ssplugins.rlstats.entities;

import org.json.JSONObject;

/**
 * Stores info about a season.
 */
public class Season {
	
	private JSONObject raw;
	private int id;
	private long startTime, endTime;
	
	public Season(JSONObject object) {
		this(object.getInt("seasonId"), object.getLong("startedOn"), object.optLong("endedOn", -1));
		raw = object;
	}
	
	private Season(int id, long startTime, long endTime) {
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	/**
	 * Get the raw {@link JSONObject} used to create the object.
	 * @return Raw {@link JSONObject}.
	 */
	public JSONObject raw() {
		return raw;
	}
	
	/**
	 * Get the ID of the season.
	 * @return Season ID.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get the time the season started.
	 * The time is in UNIX seconds.
	 * @return Time the season started.
	 */
	public long getStartTime() {
		return startTime;
	}
	
	/**
	 * Get the time the season ended.
	 * The time is in UNIX seconds.
	 * @return Time the season ended, or -1 if the season hasn't ended yet.
	 */
	public long getEndTime() {
		return endTime;
	}
	
}
