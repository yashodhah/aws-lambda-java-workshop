package com.unicorn.location;


import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Singleton
public class LocationService {
    @Inject
    DynamoDbAsyncClient dynamoDbClient;
    public void createLocationItem(UnicornLocation unicornLocation) {
        var putItemRequest = PutItemRequest.builder().item(
                        Map.of("id", AttributeValue.fromS(UUID.randomUUID().toString()),
                                "unicornName", AttributeValue.fromS(unicornLocation.getUnicornName()),
                                "latitude", AttributeValue.fromS(unicornLocation.getLatitude()),
                                "longitude", AttributeValue.fromS(unicornLocation.getLongitude())
                        ))
                .tableName("unicorn-locations")
                .build();

        try {
            dynamoDbClient.putItem(putItemRequest).get(); //<-- Add .get() call and catch the checked exceptions
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error creating Put Item request");
        }
    }
}
