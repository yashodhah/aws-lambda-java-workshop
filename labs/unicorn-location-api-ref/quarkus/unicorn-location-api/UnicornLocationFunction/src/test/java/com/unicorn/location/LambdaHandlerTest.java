package com.unicorn.location;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class LambdaHandlerTest {

    @Test
    public void shouldSaveUnicornSuccessfully() throws Exception {
        // you test your lambdas by invoking on http://localhost:8081
        // this works in dev mode too

        APIGatewayProxyRequestEvent in = new APIGatewayProxyRequestEvent();
        in.setBody("{\"unicornName\":\"Test\", \"longitude\":\"13.404954\", \"latitude\":\"52.520008\"}");

        given()
                .contentType("application/json")
                .accept("application/json")
                .body(in)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body(containsString("Received unicorn location"));
    }
}
