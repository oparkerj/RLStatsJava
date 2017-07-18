package com.ssplugins.rlstats.util;

import java.util.HashMap;
import java.util.Map;

public class Query {
	
	private Map<String, String> parameters = new HashMap<>();
	
	private Query() {}
	
	public static Query create(String key, String value) {
		return new Query().add(key, value);
	}
	
	public Query add(String key, String value) {
		if (key.isEmpty() || value.isEmpty()) return this;
		parameters.put(key, value);
		return this;
	}
	
	@Override
	public String toString() {
		if (parameters.size() == 0) return "";
		StringBuilder builder = new StringBuilder("?");
		parameters.forEach((s, s2) -> builder.append(s).append("=").append(s2).append("&"));
		return builder.substring(0, builder.length() - 1);
	}
	
}
