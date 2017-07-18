package com.ssplugins.rlstats.entities;

/**
 * Stores a player's stats for a playlist.
 */
public class PlaylistInfo {
	
	private int rankPoints, matchesPlayed, tier, division;
	
	public PlaylistInfo(int rankPoints, int matchesPlayed, int tier, int division) {
		this.rankPoints = rankPoints;
		this.matchesPlayed = matchesPlayed;
		this.tier = tier;
		this.division = division;
	}
	
	/**
	 * Get the rank points the player has in this playlist.
	 * @return Player's rank point in this playlist.
	 */
	public int getRankPoints() {
		return rankPoints;
	}
	
	/**
	 * Get the number of matches the player has in this playlist.
	 * @return Player's number of matches played in this playlist.
	 */
	public int getMatchesPlayed() {
		return matchesPlayed;
	}
	
	/**
	 * Get the tier of the player in this playlist.
	 * @return Player's tier in this playlist.
	 */
	public int getTier() {
		return tier;
	}
	
	/**
	 * Get the division of the player in this playlist.
	 * @return Player's division in this playlist.
	 */
	public int getDivision() {
		return division;
	}
	
}
