package com.ssplugins.rlstats.entities;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores info about a player.
 */
public class Player {

	private JSONObject raw;
	private String uid, displayName;
	private Platform platform;
	private String avatarUrl, profileUrl, signatureUrl;
	private Stats stats;
	private Map<Integer, SeasonInfo> seasonInfo = new HashMap<>();
	private long lastRequested, created, updated, nextUpdate;
	
	public Player(JSONObject object) {
		raw = object;
		uid = object.getString("uniqueId");
		displayName = object.getString("displayName");
		platform = Platform.fromId(object.getJSONObject("platform").getInt("id"));
		avatarUrl = object.optString("avatar", null);
		profileUrl = object.getString("profileUrl");
		signatureUrl = object.getString("signatureUrl");
		stats = new Stats(object.getJSONObject("stats"));
		JSONObject seasons = object.optJSONObject("rankedSeasons");
		if (seasons != null) {
			seasons.keySet().forEach(s -> seasonInfo.put(Integer.parseInt(s), new SeasonInfo(seasons.getJSONObject(s))));
		}
		lastRequested = object.getLong("lastRequested");
		created = object.getLong("createdAt");
		updated = object.optLong("updatedAt", -1);
		nextUpdate = object.getInt("nextUpdateAt");
	}
	
	/**
	 * Get the raw {@link JSONObject} used to create the object.
	 * @return Raw {@link JSONObject}.
	 */
	public JSONObject raw() {
		return raw;
	}
	
	/**
	 * Get the unique ID of this player.
	 * @return Unique ID of player.
	 */
	public String getUniqueId() {
		return uid;
	}
	
	/**
	 * Get the display name of this player.
	 * @return Display name of player.
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * Get the platform for this player.
	 * @return Platform of player.
	 */
	public Platform getPlatform() {
		return platform;
	}
	
	/**
	 * Get the url for the avatar of this player.
	 * @return Url for player's avatar, or null if there isn't one.
	 */
	public String getAvatarUrl() {
		return avatarUrl;
	}
	
	/**
	 * Get the url for the profile of this player.
	 * @return Url for player's profile.
	 */
	public String getProfileUrl() {
		return profileUrl;
	}
	
	/**
	 * Get the url for the signature of this player.
	 * @return Url for player's signature.
	 */
	public String getSignatureUrl() {
		return signatureUrl;
	}
	
	/**
	 * Get the stats for this player.
	 * @return The player's stats.
	 */
	public Stats getStats() {
		return stats;
	}
	
	/**
	 * Get the player's season info for all seasons they participated in.
	 * @return Player's season info for every season the player participated in.
	 */
	public Map<Integer, SeasonInfo> getAllSeasonInfo() {
		return seasonInfo;
	}
	
	/**
	 * Get the player's season info for a specific season.
	 * @param season Season to get.
	 * @return Player's season info for the specific season, or null if they did not participate in that season.
	 */
	public SeasonInfo getSeasonInfo(int season) {
		return seasonInfo.get(season);
	}
	
	/**
	 * Get the last time this player was requested.
	 * The time is in UNIX seconds.
	 * @return Time the player was last requested.
	 */
	public long getLastRequested() {
		return lastRequested;
	}
	
	/**
	 * Get the time this player was created.
	 * The time is in UNIX seconds.
	 * @return Time time the player was created.
	 */
	public long getCreated() {
		return created;
	}
	
	/**
	 * Get the last time this player was updated.
	 * The time is in UNIX seconds.
	 * @return Time the player was last updated.
	 */
	public long getUpdated() {
		return updated;
	}
	
	/**
	 * Get the next time this player will be updated.
	 * The time is in UNIX seconds.
	 * @return Time the player will be next updated.
	 */
	public long getNextUpdate() {
		return nextUpdate;
	}
}
