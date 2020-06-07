package com.github.rodbate.cloud.mini.config.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author rodbate
 * @since 2020/6/6
 */
@Configuration(proxyBeanMethods = false)
public class MiniConfigServerConfiguration {

	@Bean
	public Marker enableMiniConfigServerMarker() {
		return new Marker();
	}


	class Marker {

	}
}
