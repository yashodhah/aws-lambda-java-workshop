package com.unicorn.location.service;

import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import com.unicorn.location.model.UnicornLocation;
import jakarta.inject.Singleton;

@Singleton
public class UnicornLocationService {
    public void createUnicorn(UnicornLocation location) {

    }
}
