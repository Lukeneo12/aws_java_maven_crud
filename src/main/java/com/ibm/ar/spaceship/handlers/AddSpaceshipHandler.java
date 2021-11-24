package com.ibm.ar.spaceship.handlers;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ibm.ar.spaceship.domain.SpaceshipRepository;
import com.ibm.ar.spaceship.helpers.ApiGatewayResponse;
import com.ibm.ar.spaceship.helpers.Response;

public class AddSpaceshipHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(AddSpaceshipHandler.class);
    private SpaceshipRepository repository = new SpaceshipRepository();
	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		LOG.debug("received: {}", input);
        Map<String, Object> response = repository.addSpaceship(input);
		Response responseBody = new Response(response);
		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setObjectBody(responseBody)
				.build();
	}
}
