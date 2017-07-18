package com.ssplugins.rlstats;

import com.ssplugins.rlstats.entities.*;
import com.ssplugins.rlstats.entities.Stat;
import com.ssplugins.rlstats.util.PlayerRequest;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

public interface RLStatsAPI {
	
	/**
	 * Shuts down the executors to properly close the threads.
	 * Once called, a new instance must be created to access the API.
	 */
	void shutdownThreads();
	
	/**
	 * Sets the auth key used for API requests.
	 * @param key The key to use for API requests.
	 */
	void setAuthKey(String key);
	
	/**
	 * Returns the auth key currently being used for API requests.
	 * @return The current auth key.
	 */
	String getAuthKey();
	
	/**
	 * Sets the API version to use for API requests.
	 * @param version The version to use for API requests.
	 */
	void setAPIVersion(String version);
	
	/**
	 * Returns the API version that is currently used.
	 * @return Current API version.
	 */
	String getAPIVersion();
	
	/**
	 * Requests the list of platforms from the API.
	 * Such as: Steam, Ps4, XboxOne
	 * @return Future with a list of platforms known to the API.
	 */
	Future<List<PlatformInfo>> getPlatforms();
	
	/**
	 * Same as {@link #getPlatforms()} but blocks thread until result is returned.
	 * @return A list of platforms known to the API.
	 */
	List<PlatformInfo> getPlatformsBlocking();
	
	/**
	 * Requests the list of tiers from the API.
	 * Such as: Gold, Platinum, Diamond
	 * @return Future with list of tiers for the latest season.
	 */
	Future<List<Tier>> getTiers();
	
	/**
	 * Requests the list of tiers for a specific season.
	 * @param season Season to request tiers for.
	 * @return Future with list of tiers for the specified season.
	 */
	Future<List<Tier>> getTiers(int season);
	
	/**
	 * Same as {@link #getTiers()} but blocks thread until result is returned.
	 * @return List of tiers for the latest season.
	 */
	List<Tier> getTiersBlocking();
	
	/**
	 * Same as {@link #getTiers(int)} but blocks thread until result is returned.
	 * @param season Season to request tiers for.
	 * @return List of tiers for the specified season.
	 */
	List<Tier> getTiersBlocking(int season);
	
	/**
	 * Requests the list of seasons from the API.
	 * @return Future with list of seasons known to the API.
	 */
	Future<List<Season>> getSeasons();
	
	/**
	 * Same as {@link #getSeasons()} but blocks thread until result is returned.
	 * @return List of seasons known to the API.
	 */
	List<Season> getSeasonsBlocking();
	
	/**
	 * Requests playlist info from the API.
	 * Such as number of players in playlists.
	 * @return Future with info for known playlists.
	 */
	Future<List<Playlist>> getPlaylistInfo();
	
	/**
	 * Same as {@link #getPlaylistInfo()} but blocks thread until result is returned.
	 * @return Info for known playlists.
	 */
	List<Playlist> getPlaylistInfoBlocking();
	
	/**
	 * Requests info for a specific player.
	 * @param id Steam 64 ID, PSN Username, Xbox GamerTag, or XUID.
	 * @param platform platformId to search for.
	 * @see #getPlayer(String, Platform)
	 * @return Future with info for the specified player, or null if the player could not be found.
	 */
	Future<Player> getPlayer(String id, int platform);
	
	/**
	 * Requests info for a specific player.
	 * @param id Steam 64 ID, PSN Username, Xbox GamerTag, or XUID.
	 * @param platform Which platform to search for.
	 * @return Future with info for the specified player, or null if the player could not be found.
	 */
	Future<Player> getPlayer(String id, Platform platform);
	
	/**
	 * Same as {@link #getPlayer(String, int)} but blocks thread until result is returned.
	 * @param id Steam 64 ID, PSN Username, Xbox GamerTag, or XUID.
	 * @param platform platformId to search for.
	 * @see #getPlayerBlocking(String, Platform)
	 * @return Info for the specified player, or null if the player could not be found.
	 */
	Player getPlayerBlocking(String id, int platform);
	
	/**
	 * Same as {@link #getPlayer(String, Platform)} but blocks thread until result is returned.
	 * @param id Steam 64 ID, PSN Username, Xbox GamerTag, or XUID.
	 * @param platform Which platform to search for.
	 * @return Info for the specified player, or null if the player could not be found.
	 */
	Player getPlayerBlocking(String id, Platform platform);
	
	/**
	 * Request info for multiple players at once.
	 * If requesting players that are not in the RLS database,
	 * the connection may reach the socket timeout and return with nothing.
	 * If hitting the socket timeout, try increasing the value of {@link RLStats#SOCKET_TIMEOUT} (default: 60000L)
	 * @param collection Players to retrieve data for.
	 * @see PlayerRequest
	 * @return Future with list of data for the specified players.
	 */
	Future<List<Player>> getPlayers(Collection<PlayerRequest> collection);
	
	/**
	 * Request info for multiple players at once.
	 * If requesting players that are not in the RLS database,
	 * the connection may reach the socket timeout and return with nothing.
	 * If hitting the socket timeout, try increasing the value of {@link RLStats#SOCKET_TIMEOUT} (default: 60000L)
	 * @param requests Players to retrieve data for.
	 * @see PlayerRequest
	 * @return Future with list of data for the specified players.
	 */
	Future<List<Player>> getPlayers(PlayerRequest[] requests);
	
	/**
	 * Same as {@link #getPlayers(Collection)} but blocks thread until result is returned.
	 * @param collection Players to retrieve data for.
	 * @return List of data for the specified players.
	 */
	List<Player> getPlayersBlocking(Collection<PlayerRequest> collection);
	
	/**
	 * Same as {@link #getPlayers(PlayerRequest[])} but blocks thread until result is returned.
	 * @param requests Players to retrieve data for.
	 * @return List of data for the specified players.
	 */
	List<Player> getPlayersBlocking(PlayerRequest[] requests);
	
	/**
	 * Searches for players by display name.
	 * Only searches the RLS database, not every Rocket League player.
	 * @param displayName Display name to search for.
	 * @param page Which page of results to return.
	 * @return Future with one page worth of results.
	 */
	Future<SearchResultPage> searchPlayers(String displayName, int page);
	
	/**
	 * Searches for players by display name.
	 * Only searches the RLS database, not every Rocket League player.
	 * @param displayName Display name to search for.
	 * @return Future with the first page of results.
	 */
	Future<SearchResultPage> searchPlayers(String displayName);
	
	/**
	 * Same as {@link #searchPlayers(String, int)} but blocks thread until result is returned.
	 * @param displayName Display name to search for.
	 * @param page Which page of results to return.
	 * @return One page worth of results.
	 */
	SearchResultPage searchPlayersBlocking(String displayName, int page);
	
	/**
	 * Same as {@link #searchPlayers(String)} but blocks thread until result is returned.
	 * @param displayName Display name to search for.
	 * @return First page of results.
	 */
	SearchResultPage searchPlayersBlocking(String displayName);
	
	/**
	 * Requests list of 100 players sorted by latest season ranking.
	 * @param playlistId Which playlist to search in.
	 * @see Playlist
	 * @return 100 players sorted by latest season ranking.
	 */
	Future<List<Player>> getRankedLeaderboard(int playlistId);
	
	/**
	 * Same as {@link #getRankedLeaderboard(int)} but blocks thread until result is returned.
	 * @param playlistId Which playlist to search in.
	 * @return 100 players sorted by latest season ranking.
	 */
	List<Player> getRankedLeaderboardBlocking(int playlistId);
	
	/**
	 * Requests list of 100 players sorted by specified stat amount.
	 * @param stat Which stat to search for.
	 * @return 100 players sorted by specified stat amount.
	 */
	Future<List<Player>> getStatLeaderboard(Stat stat);
	
	/**
	 * Same as {@link #getStatLeaderboard(Stat)} but blocks thread until result is returned.
	 * @param stat Which stat to search for.
	 * @return 100 players sorted by specified stat amount.
	 */
	List<Player> getStatLeaderboardBlocking(Stat stat);
	
}
