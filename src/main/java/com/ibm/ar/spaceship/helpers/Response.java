package com.ibm.ar.spaceship.helpers;

import java.util.Map;

public class Response {

	private final Map<String, Object> response;

	public Response(Map<String, Object> response) {
		this.response = response;
	}

	public Map<String, Object> getResponse() {
		return this.response;
	}
}
