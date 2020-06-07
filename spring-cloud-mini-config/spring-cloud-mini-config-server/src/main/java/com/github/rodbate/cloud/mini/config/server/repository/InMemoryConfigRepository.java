package com.github.rodbate.cloud.mini.config.server.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.github.rodbate.cloud.mini.config.common.PropertySource;
import com.github.rodbate.cloud.mini.config.common.PropertySources;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * in memory config repository
 *
 * @author rodbate
 * @since 2020/6/6
 */
public class InMemoryConfigRepository implements ConfigRepository {

	private final ConcurrentMap<String /* namespace */, ConcurrentMap<String /* config name */, Map<?, ?>>> configData
			= new ConcurrentHashMap<>(128);

	@Override
	public PropertySources getConfig(String namespace, Set<String> configNames) {
		Assert.isTrue(StringUtils.hasText(namespace), "namespace require not null");
		final PropertySources propertySources = new PropertySources();
		propertySources.setNamespace(namespace);
		final List<PropertySource> sourceList = new ArrayList<>();
		propertySources.setPropertySourceList(sourceList);

		final ConcurrentMap<String, Map<?, ?>> propertyMap = configData.get(namespace);
		if (null != propertyMap) {
			if (CollectionUtils.isEmpty(configNames)) {
				propertyMap.forEach((name, props) -> sourceList.add(new PropertySource(name, props)));
			} else {
				propertyMap.entrySet().stream().filter(entry -> configNames.contains(entry.getKey()))
						.forEach(entry -> sourceList.add(new PropertySource(entry.getKey(), entry.getValue())));
			}
		}

		return propertySources;
	}

	@Override
	public void publishConfig(PropertySources propertySources) {
		ConcurrentMap<String, Map<?, ?>> configMap = configData
				.computeIfAbsent(propertySources.getNamespace(), k -> new ConcurrentHashMap<>());
		Optional.ofNullable(propertySources.getPropertySourceList()).orElse(Collections.emptyList())
				.forEach(propertySource -> configMap.put(propertySource.getName(), propertySource.getSource()));
	}

}
