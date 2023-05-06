package com.unicorn.location;

import com.unicorn.location.UnicornLocation;
import jakarta.inject.Singleton;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Singleton
public class UnicornLocationService {
    private final DynamoDbAsyncClient dynamoDbAsyncClient;

    public UnicornLocationService(DynamoDbAsyncClient dynamoDbAsyncClient) {
        this.dynamoDbAsyncClient = dynamoDbAsyncClient;
    }
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
            dynamoDbAsyncClient.putItem(putItemRequest).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error creating Put Item request");
        }
    }
}
