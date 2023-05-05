package com.unicorn.location;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.jr.ob.JSON;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("UnicornPostLocationHandler")
public class UnicornPostLocationHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final Logger logger = LoggerFactory.getLogger(UnicornPostLocationHandler.class);

    @Inject
    LocationService locationService;
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        try {
            UnicornLocation unicornLocation = JSON.std.beanFrom(UnicornLocation.class, input.getBody());
            locationService.createLocationItem(unicornLocation);

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
