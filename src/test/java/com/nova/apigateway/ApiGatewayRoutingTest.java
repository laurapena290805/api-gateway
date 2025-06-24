package com.nova.apigateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
class ApiGatewayRoutingTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testUserRouteIsRouted() {
        // Arrange: Stubbing WireMock for a downstream service
        stubFor(get(urlEqualTo("/api/user/1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"userId\":\"1\",\"name\":\"Test User\"}")
                        .withStatus(200)));

        // Act & Assert: Making the request through the gateway and verifying the response
        webTestClient
                .get().uri("/api/user/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.userId").isEqualTo("1")
                .jsonPath("$.name").isEqualTo("Test User");
    }

    @Test
    void testProductRouteIsRouted() {
        stubFor(get(urlEqualTo("/api/productos/5"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"productId\":\"5\",\"name\":\"Test Product\"}")
                        .withStatus(200)));

        webTestClient
                .get().uri("/api/productos/5")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.productId").isEqualTo("5")
                .jsonPath("$.name").isEqualTo("Test Product");
    }

    @Test
    void testOrderRouteIsRouted() {
        stubFor(get(urlEqualTo("/api/ordenes/10"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"orderId\":\"10\",\"status\":\"SHIPPED\"}")
                        .withStatus(200)));

        webTestClient
                .get().uri("/api/ordenes/10")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.orderId").isEqualTo("10")
                .jsonPath("$.status").isEqualTo("SHIPPED");
    }

    @Test
    void testDeliveryRouteIsRouted() {
        stubFor(get(urlEqualTo("/entregas/15"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"deliveryId\":\"15\",\"status\":\"IN_TRANSIT\"}")
                        .withStatus(200)));

        webTestClient
                .get().uri("/entregas/15")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.deliveryId").isEqualTo("15")
                .jsonPath("$.status").isEqualTo("IN_TRANSIT");
    }
} 