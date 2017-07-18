package com.ssplugins.rlstats.entities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResultPage {
	
	private JSONObject raw;
	private int page, numResults, totalResults, maxResultsPerPage;
	private List<Player> results = new ArrayList<>();
	
	public SearchResultPage(JSONObject object) {
		raw = object;
		page = object.getInt("page");
		numResults = object.getInt("results");
		totalResults = object.getInt("totalResults");
		maxResultsPerPage = object.getInt("maxResultsPerPage");
		JSONArray arr = object.getJSONArray("data");
		arr.forEach(o -> results.add(new Player((JSONObject) o)));
	}
	
	public JSONObject raw() {
		return raw;
	}
	
	public int getPage() {
		return page;
	}
	
	public int getNumResults() {
		return numResults;
	}
	
	public int getTotalResults() {
		return totalResults;
	}
	
	public int getMaxResultsPerPage() {
		return maxResultsPerPage;
	}
	
	public List<Player> getResults() {
		return results;
	}
	
}
