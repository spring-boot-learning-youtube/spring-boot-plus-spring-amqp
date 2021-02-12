package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ContextConfiguration
public class RabbitTest {

	@Container static RabbitMQContainer container = new RabbitMQContainer(
			DockerImageName.parse("rabbitmq").withTag("3.7.25-management-alpine"));

	// Grab Testcontainers info and inject it into Spring Boot
	@DynamicPropertySource
	static void configure(DynamicPropertyRegistry registry) {
		registry.add("spring.rabbitmq.host", container::getContainerIpAddress);
		registry.add("spring.rabbitmq.port", container::getAmqpPort);
	}

	@Autowired
	StockWatcher stockWatcher;

	@Test
	void rabbitTest() throws InterruptedException {
		// Give the test time to send some messages.
		Thread.sleep(5000);

		assertThat(this.stockWatcher.getTrades()).hasSizeGreaterThan(0);

		// This is pretty bare. What are some assertions YOU could write?
	}
}
