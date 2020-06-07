package com.github.rodbate.cloud.mini.config.server.repository;

import java.util.Set;

import com.github.rodbate.cloud.mini.config.common.PropertySources;

/**
 *
 * @author rodbate
 * @since 2020/6/6
 */
public interface ConfigRepository {

	/**
	 * get config
	 *
	 * @param namespace   config namespace
	 * @param configNames config names
	 * @return config property sources
	 */
	PropertySources getConfig(String namespace, Set<String> configNames);


	/**
	 * publish config
	 *
	 * @param propertySources config property sources
	 */
	void publishConfig(PropertySources propertySources);
}
