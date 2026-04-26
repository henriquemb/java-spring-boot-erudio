package com.github.henriquemb.integrationtests.testcontainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.mysql.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {
	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		static MySQLContainer mysql = new MySQLContainer(DockerImageName.parse("mariadb:12"));

		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			startContainers();
			ConfigurableEnvironment environment = applicationContext.getEnvironment();
			MapPropertySource mapPropertySource = new MapPropertySource("testcontainers", createConnectionConfiguration());
			environment.getPropertySources().addFirst(mapPropertySource);
		}

		private static Map<String, Object> createConnectionConfiguration() {
			Map<String, Object> map = new HashMap<>();
			map.put("spring.datasource.url", mysql.getJdbcUrl());
			map.put("spring.datasource.username", mysql.getUsername());
			map.put("spring.datasource.password", mysql.getPassword());

			return map;
		}

		private static void startContainers() {
			Startables.deepStart(Stream.of(mysql)).join();
		}
	}
}
