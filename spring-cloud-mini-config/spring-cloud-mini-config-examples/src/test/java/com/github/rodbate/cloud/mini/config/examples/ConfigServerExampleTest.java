package com.github.rodbate.cloud.mini.config.examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.rodbate.cloud.mini.config.common.PropertySource;
import com.github.rodbate.cloud.mini.config.common.PropertySources;
import com.github.rodbate.cloud.mini.config.server.MiniConfigServerApplication;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author rodbate
 * @since 2020/6/6
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConfigServerExampleTest {

	private static ConfigurableApplicationContext server;

	@LocalServerPort
	private int localPort;

	@BeforeClass
	public static void startConfigServer() {
		server = new SpringApplicationBuilder(MiniConfigServerApplication.class)
				.properties("spring.config.name=miniconfigserver")
				.run();
		publishConfig();
	}

	private static void publishConfig() {
		new TestRestTemplate().postForObject("http://localhost:8888/config/publish",
				buildTestProperties(), String.class);
	}

	private static PropertySources buildTestProperties() {
		PropertySources propertySources = new PropertySources();
		propertySources.setNamespace("test");
		List<PropertySource> list = new ArrayList<>();
		propertySources.setPropertySourceList(list);
		Map<String, String> data = new HashMap<>();
		data.put("test.name", "example-test");
		list.add(new PropertySource("test.properties", data));
		return propertySources;
	}

	public static void main(String[] args) {
		startConfigServer();
		SpringApplication.run(ExampleApplication.class, args);
	}

	@Test
	public void test() {
		String value = new TestRestTemplate()
				.getForObject("http://localhost:" + localPort + "/test/name", String.class);
		Assertions.assertEquals("example-test", value);
	}
}
