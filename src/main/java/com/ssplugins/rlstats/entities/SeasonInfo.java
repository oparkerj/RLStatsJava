package com.ssplugins.rlstats.entities;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SeasonInfo {
	
	private Map<Integer, PlaylistInfo> map = new HashMap<>();
	
	public SeasonInfo(JSONObject object) {
		object.keySet().forEach(s -> {
			JSONObject data = object.getJSONObject(s);
			map.put(Integer.parseInt(s), new PlaylistInfo(
					data.optInt("rankPoints", -1),
					data.optInt("matchesPlayed", -1),
					data.optInt("tier", -1),
					data.optInt("division", -1)
					));
		});
	}
	
	public Map<Integer, PlaylistInfo> getAllPlaylistInfo() {
		return Collections.unmodifiableMap(map);
	}
	
	public PlaylistInfo getPlaylistInfo(int playlistId) {
		return map.get(playlistId);
	}
	
}
