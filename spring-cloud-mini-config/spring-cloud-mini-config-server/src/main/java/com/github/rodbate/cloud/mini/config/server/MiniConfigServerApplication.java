package com.github.rodbate.cloud.mini.config.server;

import com.github.rodbate.cloud.mini.config.server.annotation.EnableMiniConfigServer;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;

/**
 * Config server application
 *
 * @author rodbate
 * @since 2020/5/31
 */
@Configuration(proxyBeanMethods = false)
@EnableMiniConfigServer
@EnableAutoConfiguration
public class MiniConfigServerApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(MiniConfigServerApplication.class)
				.properties("spring.config.name=miniconfigserver")
				.run(args);
	}
}
