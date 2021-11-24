package com.ibm.ar.spaceship.persistence.pojo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

@DynamoDBTable(tableName = "spaceshipcrud-dev-spaceship-table")
public class Spaceship {

    @DynamoDBHashKey(attributeName = "spaceship_id")
    private String spaceshipId;

    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @DynamoDBAttribute(attributeName = "color")
    private String color;

    @DynamoDBAttribute(attributeName = "capacity")
    private Integer capacity;

    @DynamoDBAttribute(attributeName = "engine")
    private String engine;

   public String getSpaceshipId() {
       return spaceshipId;
   }

   public void setSpaceshipId(String spaceshipId) {
       this.spaceshipId = spaceshipId;
   }

   public String getName() {
       return name;
   }

   public void setName(String name) {
       this.name = name;
   }

   public String getColor() {
        return color;
   }

   public void setColor(String color) {
       this.color = color;
   }

   public Integer getCapacity() {
    return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getEngine() {
        return engine;
        }
    public void setEngine(String engine) {
        this.engine = engine;
    }
}
