package com.ibm.ar.spaceship.domain;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.ar.spaceship.persistence.adapter.DynamoDBAdapter;
import com.ibm.ar.spaceship.persistence.pojo.Spaceship;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpaceshipRepository {
    private static final Logger LOG = LogManager.getLogger(SpaceshipRepository.class);
    private Map<String, Object> response;
    private Spaceship spaceship;
    private ObjectMapper mapper;

    public Map<String, Object> addSpaceship(Map<String, Object> input) {
        mapper = new ObjectMapper();
        spaceship = new Spaceship();
        UUID uuid = UUID.randomUUID();
        response = new HashMap<>();
        try {
            JsonNode body = mapper.readTree((String) input.get("body"));

            spaceship.setSpaceshipId(uuid.toString());
            spaceship.setName(body.get("name").asText());
            spaceship.setColor(body.get("color").asText());
            spaceship.setCapacity(body.get("capacity").asInt());
            spaceship.setEngine(body.get("engine").asText());

            DynamoDBAdapter.getInstance().createItem(spaceship);

            response.put("spaceship", spaceship);
            response.put("message", "Success creation of spaceship");
            return response;
        } catch (Exception e) {
            LOG.error("Failed to created a spaceship: "+e.getMessage());
            response.put("error", e.getMessage());
            response.put("code", "failed_to_create_spaceship");
            response.put("severity", "HIGH");
            return response;
        }
    }

    public Map<String, Object> getSpaceshipById(String spaceshipId) {
        response = new HashMap<>();
        try {
            spaceship = DynamoDBAdapter.getInstance().getSpaceshipById(spaceshipId, "spaceship_id");
            response.put("spaceship", spaceship);
            response.put("message", "Get of a spaceship successful");
            return response;
        } catch (Exception e) {
            LOG.error("Failed to get a spaceship: "+e.getMessage());
            response.put("error", e.getMessage());
            response.put("code", "failed_to_get_spaceship");
            response.put("severity", "HIGH");
            return response;
        }
    }

    public Map<String, Object> deleteSpaceshipById(String spaceshipId) {
        response = new HashMap<>();
        try {
            spaceship = DynamoDBAdapter.getInstance().getSpaceshipById(spaceshipId, "spaceship_id");
            DynamoDBAdapter.getInstance().deleteItem(spaceship);
            response.put("message", "Delete a spaceship successful");
            return response;
        } catch (Exception e) {
            LOG.error("Failed to delete a spaceship: "+e.getMessage());
            response.put("error ", e.getMessage());
            response.put("code", "failed_to_delete_spaceship");
            response.put("severity", "HIGH");
            return response;
        }
    }

    public Map<String, Object> updateSpaceshipById(String spaceshipId, Map<String, Object> input) {
        response = new HashMap<>();
        mapper = new ObjectMapper();
        try {
            spaceship = DynamoDBAdapter.getInstance().getSpaceshipById(spaceshipId, "spaceship_id");


            // In this case we could iterate the JsonNode to get the values and check if is need to be updated
            JsonNode body = mapper.readTree((String) input.get("body"));
            if(body.get("name") != null && body.get("name").asText().trim() != "")
                spaceship.setName(body.get("name").asText());

            if(body.get("color") != null && body.get("color").asText().trim() != "")
                spaceship.setColor(body.get("color").asText());

            if(body.get("capacity") != null)
                spaceship.setCapacity(body.get("capacity").asInt());

            if(body.get("engine") != null && body.get("engine").asText().trim() != "")
                spaceship.setEngine(body.get("engine").asText());

            DynamoDBAdapter.getInstance().updateItem(spaceship);
            response.put("message", "Update of a spaceship successful");
            response.put("spaceship", spaceship);
            return response;
        } catch (Exception e) {
            LOG.error("Failed to delete a spaceship: "+e.getMessage());
            response.put("error ", e.getMessage());
            response.put("code", "failed_to_update_spaceship");
            response.put("severity", "HIGH");
            return response;
        }
    }
}
