package com.ibm.ar.spaceship.persistence.adapter;


import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.ibm.ar.spaceship.persistence.pojo.Spaceship;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class DynamoDBAdapter {
    private static final Logger LOG = LogManager.getLogger(DynamoDBMapper.class);

    private final static DynamoDBAdapter adapter = new DynamoDBAdapter();

    private final AmazonDynamoDB client;

    private DynamoDBAdapter() {
        client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration("https://dynamodb.us-east-1.amazonaws.com", "us-east-1"))
                .build();
        LOG.info("Created DynamoDB client");
    }

    public static DynamoDBAdapter getInstance() {
        return adapter;
    }

    public void createItem(Object payload) throws IOException {
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        mapper.save(payload);
    }

    // TODO: Refactor this code for abstraction
    // Just a getItem of a dynamoDB table and not a specific Class
    // Bad practice
    public Spaceship getSpaceshipById(String id, String conditionValue) throws IOException {
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        Map<String, AttributeValue> values = new HashMap<>();
        values.put(":id", new AttributeValue().withS(id));
        DynamoDBQueryExpression<Spaceship> queryExpression = new DynamoDBQueryExpression<Spaceship>()
            .withKeyConditionExpression(conditionValue+" = :id")
            .withExpressionAttributeValues(values);
        return (mapper.query(Spaceship.class, queryExpression)).get(0);
    }

    public void deleteItem(Object payload) {
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        mapper.delete(payload);
    }

    public void updateItem(Object payload) {
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        mapper.save(payload);
    }


}
