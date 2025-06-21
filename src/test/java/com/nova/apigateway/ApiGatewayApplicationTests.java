package com.nova.apigateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.cloud.discovery.enabled=false"})
class ApiGatewayApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void contextLoads() {
	}

	@Test
	public void testUserRoute() {
		webTestClient
				.get().uri("/api/user/1")
				.exchange()
				.expectStatus().is5xxServerError(); // Esperamos un error 500 ya que el servicio no esta levantado
	}
}
