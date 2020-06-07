package com.github.rodbate.cloud.mini.config.server.controller;

import java.util.Set;

import com.github.rodbate.cloud.mini.config.common.PropertySources;
import com.github.rodbate.cloud.mini.config.server.exception.ConfigWebException;
import com.github.rodbate.cloud.mini.config.server.repository.ConfigRepository;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * configs web controller
 *
 * @author rodbate
 * @since 2020/6/6
 */
@RestController
public class ConfigController {

	private final ConfigRepository configRepository;

	public ConfigController(ConfigRepository configRepository) {
		this.configRepository = configRepository;
	}

	@GetMapping("/get")
	public PropertySources getConfig(@RequestParam String namespace,
									 @RequestParam(required = false) Set<String> configNames) {
		if (!StringUtils.hasText(namespace)) {
			throw new ConfigWebException("namespace require not null");
		}
		return configRepository.getConfig(namespace, configNames);
	}


	@PostMapping("/publish")
	public String publishConfig(@RequestBody PropertySources propertySources) {
		if (!StringUtils.hasText(propertySources.getNamespace())) {
			throw new ConfigWebException("namespace require not null");
		}
		configRepository.publishConfig(propertySources);
		return "ok";
	}
}
