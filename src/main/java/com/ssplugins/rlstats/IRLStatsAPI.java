package com.ssplugins.rlstats;

import com.mashape.unirest.http.JsonNode;
import com.ssplugins.rlstats.entities.*;
import com.ssplugins.rlstats.util.PlayerRequest;
import com.ssplugins.rlstats.util.Query;
import com.ssplugins.rlstats.util.RequestQueue;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Supplier;

public class IRLStatsAPI implements RLStatsAPI {
	
	private final ExecutorService tasks = Executors.newCachedThreadPool();
	
	private RequestQueue queue = new RequestQueue();
	
	private String key = null;
	private String apiVersion = "v1";
	private boolean shutdown = false;
	
	IRLStatsAPI() {}
	
	private void basicCheck() {
		if (shutdown)
			throw new IllegalStateException("Threads have been shutdown. Create a new instance to make more requests");
		if (key == null || key.isEmpty()) throw new IllegalStateException("No API key was provided.");
	}
	
	// Utility Methods
	
	private <T> T getMethodBlocking(Future<T> future, Supplier<T> fallback) {
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return fallback.get();
	}
	
	private <T> List<T> jsonNodeToObjectList(JsonNode node, Function<JSONObject, T> converter) {
		List<T> list = new ArrayList<>();
		node.getArray().forEach(o -> list.add(converter.apply((JSONObject) o)));
		return list;
	}
	
	//
	
	@Override
	public void shutdownThreads() {
		shutdown = true;
		queue.shutdown();
		tasks.shutdownNow();
	}
	
	@Override
	public void setAuthKey(String key) {
		this.key = key;
	}
	
	@Override
	public String getAuthKey() {
		return key;
	}
	
	@Override
	public void setAPIVersion(String version) {
		this.apiVersion = version;
	}
	
	@Override
	public String getAPIVersion() {
		return apiVersion;
	}
	
	@Override
	public Future<List<PlatformInfo>> getPlatforms() {
		basicCheck();
		return tasks.submit(() -> {
			Future<JsonNode> response = queue.get(key, apiVersion, "/data/platforms", null);
			try {
				return jsonNodeToObjectList(response.get(), PlatformInfo::new);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return new ArrayList<>();
		});
	}
	
	@Override
	public List<PlatformInfo> getPlatformsBlocking() {
		return getMethodBlocking(getPlatforms(), ArrayList::new);
	}
	
	@Override
	public Future<List<Tier>> getTiers() {
		basicCheck();
		return tasks.submit(() -> {
			Future<JsonNode> response = queue.get(key, apiVersion, "/data/tiers", null);
			return getTiers(response);
		});
	}
	
	@Override
	public Future<List<Tier>> getTiers(int season) {
		basicCheck();
		return tasks.submit(() -> {
			Future<JsonNode> response = queue.get(key, apiVersion, "/data/tiers/" + season, null);
			return getTiers(response);
		});
	}
	
	private List<Tier> getTiers(Future<JsonNode> response) {
		try {
			return jsonNodeToObjectList(response.get(), Tier::new);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	@Override
	public List<Tier> getTiersBlocking() {
		return getMethodBlocking(getTiers(), ArrayList::new);
	}
	
	@Override
	public List<Tier> getTiersBlocking(int season) {
		return getMethodBlocking(getTiers(season), ArrayList::new);
	}
	
	@Override
	public Future<List<Season>> getSeasons() {
		basicCheck();
		return tasks.submit(() -> {
			Future<JsonNode> response = queue.get(key, apiVersion, "/data/seasons", null);
			try {
				return jsonNodeToObjectList(response.get(), Season::new);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return new ArrayList<>();
		});
	}
	
	@Override
	public List<Season> getSeasonsBlocking() {
		return getMethodBlocking(getSeasons(), ArrayList::new);
	}
	
	@Override
	public Future<List<Playlist>> getPlaylistInfo() {
		basicCheck();
		return tasks.submit(() -> {
			Future<JsonNode> response = queue.get(key, apiVersion, "/data/playlists", null);
			try {
				return jsonNodeToObjectList(response.get(), Playlist::new);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return new ArrayList<>();
		});
	}
	
	@Override
	public List<Playlist> getPlaylistInfoBlocking() {
		return getMethodBlocking(getPlaylistInfo(), ArrayList::new);
	}
	
	@Override
	public Future<Player> getPlayer(String id, int platform) {
		basicCheck();
		return tasks.submit(() -> {
			Future<JsonNode> response = queue.get(key, apiVersion, "/player", Query.create("unique_id", id).add("platform_id", String.valueOf(platform)));
			try {
				JsonNode node = response.get();
				return new Player(node.getObject());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return null;
		});
	}
	
	@Override
	public Future<Player> getPlayer(String id, Platform platform) {
		return getPlayer(id, platform.getId());
	}
	
	@Override
	public Player getPlayerBlocking(String id, int platform) {
		return getMethodBlocking(getPlayer(id, platform), () -> null);
	}
	
	@Override
	public Player getPlayerBlocking(String id, Platform platform) {
		return getPlayerBlocking(id, platform.getId());
	}
	
	@Override
	public Future<List<Player>> getPlayers(Collection<PlayerRequest> collection) {
		if (collection.size() > 10) throw new IllegalArgumentException("Cannot have more than 10 players requested.");
		basicCheck();
		return tasks.submit(() -> {
			JSONArray array = new JSONArray();
			collection.forEach(playerRequest -> array.put(playerRequest.toJSONObject()));
			Future<JsonNode> response = queue.post(key, apiVersion, "/player/batch", null, array.toString());
			try {
				return jsonNodeToObjectList(response.get(), Player::new);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return new ArrayList<>();
		});
	}
	
	@Override
	public Future<List<Player>> getPlayers(PlayerRequest[] requests) {
		return getPlayers(Arrays.asList(requests));
	}
	
	@Override
	public List<Player> getPlayersBlocking(Collection<PlayerRequest> collection) {
		return getMethodBlocking(getPlayers(collection), ArrayList::new);
	}
	
	@Override
	public List<Player> getPlayersBlocking(PlayerRequest[] requests) {
		return getMethodBlocking(getPlayers(requests), ArrayList::new);
	}
	
	@Override
	public Future<SearchResultPage> searchPlayers(String displayName, int page) {
		basicCheck();
		return tasks.submit(() -> {
			Future<JsonNode> response = queue.get(key, apiVersion, "/search/players", Query.create("display_name", displayName).add("page", String.valueOf(page)));
			try {
				JsonNode node = response.get();
				return new SearchResultPage(node.getObject());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return null;
		});
	}
	
	@Override
	public Future<SearchResultPage> searchPlayers(String displayName) {
		return searchPlayers(displayName, 0);
	}
	
	@Override
	public SearchResultPage searchPlayersBlocking(String displayName, int page) {
		return getMethodBlocking(searchPlayers(displayName, page), () -> null);
	}
	
	@Override
	public SearchResultPage searchPlayersBlocking(String displayName) {
		return getMethodBlocking(searchPlayers(displayName), () -> null);
	}
	
	@Override
	public Future<List<Player>> getRankedLeaderboard(int playlistId) {
		basicCheck();
		return tasks.submit(() -> {
			Future<JsonNode> response = queue.get(key, apiVersion, "/leaderboard/ranked", Query.create("playlist_id", String.valueOf(playlistId)));
			try {
				return jsonNodeToObjectList(response.get(), Player::new);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return new ArrayList<>();
		});
	}
	
	@Override
	public List<Player> getRankedLeaderboardBlocking(int playlistId) {
		return getMethodBlocking(getRankedLeaderboard(playlistId), ArrayList::new);
	}
	
	@Override
	public Future<List<Player>> getStatLeaderboard(Stat stat) {
		if (stat == null) throw new IllegalArgumentException("Stat parameter is null.");
		basicCheck();
		return tasks.submit(() -> {
			Future<JsonNode> response = queue.get(key, apiVersion, "/leaderboard/stat", Query.create("type", stat.getQueryName()));
			try {
				return jsonNodeToObjectList(response.get(), Player::new);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return new ArrayList<>();
		});
	}
	
	@Override
	public List<Player> getStatLeaderboardBlocking(Stat stat) {
		return getMethodBlocking(getStatLeaderboard(stat), ArrayList::new);
	}
}
