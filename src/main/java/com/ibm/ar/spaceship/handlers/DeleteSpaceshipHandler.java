package com.ibm.ar.spaceship.handlers;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ibm.ar.spaceship.domain.SpaceshipRepository;
import com.ibm.ar.spaceship.helpers.ApiGatewayResponse;
import com.ibm.ar.spaceship.helpers.Response;

public class DeleteSpaceshipHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(DeleteSpaceshipHandler.class);
    private SpaceshipRepository repository = new SpaceshipRepository();
	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		LOG.debug("received: {}", input);

		@SuppressWarnings("unchecked")
		Map<String, String> pathParameters = (Map<String, String>) input.get("pathParameters");
        String spaceshipId = pathParameters.get("spaceship_id");
        Map<String, Object> response = repository.deleteSpaceshipById(spaceshipId);
		Response responseBody = new Response(response);
		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setObjectBody(responseBody)
				.build();
	}
}
