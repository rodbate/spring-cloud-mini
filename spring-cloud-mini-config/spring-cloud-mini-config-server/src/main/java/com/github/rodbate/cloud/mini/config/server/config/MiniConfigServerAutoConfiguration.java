package com.github.rodbate.cloud.mini.config.server.config;

import com.github.rodbate.cloud.mini.config.server.controller.ConfigController;
import com.github.rodbate.cloud.mini.config.server.repository.ConfigRepository;
import com.github.rodbate.cloud.mini.config.server.repository.InMemoryConfigRepository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author rodbate
 * @since 2020/6/6
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(MiniConfigServerConfiguration.Marker.class)
public class MiniConfigServerAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public ConfigRepository inMemoryConfigRepository() {
		return new InMemoryConfigRepository();
	}

	@Bean
	public ConfigController configController(ConfigRepository configRepository) {
		return new ConfigController(configRepository);
	}
}
