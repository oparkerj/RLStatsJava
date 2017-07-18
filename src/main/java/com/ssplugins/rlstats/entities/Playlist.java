package com.ssplugins.rlstats.entities;

import com.ssplugins.rlstats.util.NameIDPair;
import org.json.JSONObject;

/**
 * Represents a Rocket League playlist.
 */
public class Playlist extends NameIDPair {
	
	public static final int DUEL = 1;
	public static final int DOUBLES = 2;
	public static final int STANDARD = 3;
	public static final int CHAOS = 4;
	public static final int RANKED_DUEL = 10;
	public static final int RANKED_DOUBLES = 11;
	public static final int RANKED_SOLO_STANDARD = 12;
	public static final int RANKED_STANDARD = 13;
	public static final int MUTATOR_MASHUP = 14;
	public static final int SNOW_DAY = 15;
	public static final int ROCKET_LABS = 16;
	public static final int HOOPS = 17;
	public static final int RUMBLE = 18;
	public static final int DROPSHOT = 23;
	
	private JSONObject raw;
	private int players;
	private int platform;
	private long lastUpdated;
	
	public Playlist(JSONObject object) {
		this(object.getString("name"), object.getInt("id"), object.getInt("platformId"), object.getJSONObject("population").getInt("players"), object.getJSONObject("population").getLong("updatedAt"));
		raw = object;
	}
	
	private Playlist(String name, int id, int platform, int players, long lastUpdated) {
		super(name, id);
		this.platform = platform;
		this.players = players;
		this.lastUpdated = lastUpdated;
	}
	
	/**
	 * Get the raw {@link JSONObject} used to create the object.
	 * @return Raw {@link JSONObject}.
	 */
	public JSONObject raw() {
		return raw;
	}
	
	/**
	 * Get which platform this playlist is for.
	 * @return Platform this playlist is for, or null if it couldn't be found.
	 */
	public Platform getPlatform() {
		return Platform.fromId(platform);
	}
	
	/**
	 * Get the ID of the platform this playlist is for.
	 * @return ID of the platform this playlist is for.
	 */
	public int getPlatformId() {
		return platform;
	}
	
	/**
	 * Get the number of players in this playlist.
	 * @return Number of players in the playlist.
	 */
	public int getPlayers() {
		return players;
	}
	
	/**
	 * Get the time the playlist was last updated.
	 * The time is in UNIX seconds.
	 * @return Time playlist was last updated.
	 */
	public long getLastUpdated() {
		return lastUpdated;
	}
	
}
