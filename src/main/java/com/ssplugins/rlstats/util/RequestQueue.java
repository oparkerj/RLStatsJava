package com.ssplugins.rlstats.util;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.ssplugins.rlstats.RLStats;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RequestQueue {
	
	private final ExecutorService service = Executors.newSingleThreadExecutor();
	
	private int requestsLeft;
	private long msRemaining, lastRequest;
	
	public RequestQueue() {
		this.requestsLeft = 2;
		this.msRemaining = -1;
	}
	
	public void shutdown() {
		service.shutdownNow();
	}
	
	public Future<JsonNode> post(String apiKey, String apiVersion, String endpoint, Query query, String body) {
		return request(apiKey, apiVersion, endpoint, query, body, true);
	}
	
	public Future<JsonNode> get(String apiKey, String apiVersion, String endpoint, Query query) {
		return request(apiKey, apiVersion, endpoint, query, null, false);
	}
	
	private Future<JsonNode> request(String apiKey, String apiVersion, String endpoint, Query query, String body, boolean post) {
		String url = RLStats.BASE_URL + apiVersion + endpoint + (query == null ? "" : query.toString());
		System.out.println("Requesting: " + url);
		return service.submit(() -> {
			if (requestsLeft == 0) {
				long time = System.currentTimeMillis();
				long delta = time - lastRequest;
				if (delta < msRemaining) {
					try {
						Thread.sleep(msRemaining - delta);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				Unirest.setTimeouts(RLStats.CONNECTION_TIMEOUT, RLStats.SOCKET_TIMEOUT);
				HttpResponse<JsonNode> response;
				if (post) {
					HttpRequestWithBody requestWithBody = Unirest.post(url).header("Authorization", apiKey).header("Content-Type", "application/json");
					if (body != null) requestWithBody.body(body);
					response = requestWithBody.asJson();
				}
				else {
					response = Unirest.get(url).header("Authorization", apiKey).asJson();
				}
				checkStatus(response);
				Headers headers = response.getHeaders();
				requestsLeft = Integer.parseInt(headers.getFirst("X-Rate-Limit-Remaining"));
				msRemaining = Long.parseLong(headers.getFirst("X-Rate-Limit-Reset-Remaining"));
				lastRequest = System.currentTimeMillis();
				return response.getBody();
			} catch (UnirestException e) {
				e.printStackTrace();
			}
			return null;
		});
	}
	
	private void checkStatus(HttpResponse<JsonNode> response) {
		int code = response.getStatus();
		switch (code) {
			case 200:
				break;
			case 400:
				throw new IllegalStateException("Bad Request. Possibly Invalid (E:400)");
			case 401:
				throw new IllegalStateException("Unauthorized. API key is wrong. (E:401)");
			case 404:
				throw new IllegalStateException("Not Found. (E:404)");
			case 405:
				throw new IllegalStateException("Method Not Allowed. Attempted access with invalid method. (E:405)");
			case 406:
				throw new IllegalStateException("Not Acceptable. Requested format that isn't json. (E:406)");
			case 429:
				throw new IllegalStateException("Too Many Requests. Retry after " + response.getHeaders().getFirst("retry-after-ms") + "ms (E:429)");
			case 500:
				throw new IllegalStateException("Internal Server Error. (E:500) Message: " + response.getBody().getObject().getString("message"));
			case 503:
				throw new IllegalStateException("Service Unavailable. Temporarily offline for maintentance.");
			default:
				break;
		}
	}
	
}
