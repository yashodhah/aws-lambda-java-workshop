package com.unicorn.location.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.jr.ob.JSON;
import com.unicorn.location.model.UnicornLocation;
import com.unicorn.location.service.UnicornLocationService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.function.aws.MicronautRequestHandler;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Introspected
public class UnicornPostRequestHandler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final Logger logger = LoggerFactory.getLogger(UnicornPostRequestHandler.class);

    @Inject
    private UnicornLocationService unicornLocationService;

    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {
        try {
            UnicornLocation unicornLocation = JSON.std.beanFrom(UnicornLocation.class, input.getBody());
            unicornLocationService.createUnicorn(unicornLocation);

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody("Received unicorn location: " + JSON.std.asString(unicornLocation));

        } catch (Exception e) {
            logger.error("Error while processing the request", e);

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(400)
                    .withBody("Error processing the request");
        }
    }
}