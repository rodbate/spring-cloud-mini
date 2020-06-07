package com.github.rodbate.cloud.mini.config.client;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import com.github.rodbate.cloud.mini.config.common.PropertySources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.web.client.RestTemplate;

/**
 * Mini Config property source locator
 *
 * Fetch config properties from remote config server
 *
 * @author rodbate
 * @since 2020/5/31
 */
@Order(0)
public class MiniConfigPropertySourceLocator implements PropertySourceLocator {
	private static final Logger log = LoggerFactory.getLogger(MiniConfigPropertySourceLocator.class);

	private final RestTemplate restTemplate;
	private final MiniConfigClientProperties miniConfigClientProperties;

	public MiniConfigPropertySourceLocator(RestTemplate restTemplate, MiniConfigClientProperties miniConfigClientProperties) {
		this.restTemplate = restTemplate;
		this.miniConfigClientProperties = miniConfigClientProperties;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PropertySource<?> locate(Environment environment) {
		final CompositePropertySource composite = new CompositePropertySource("miniConfigService");
		PropertySources remote = getRemoteConfigPropertySources();
		if (null != remote) {
			Optional.ofNullable(remote.getPropertySourceList()).orElse(Collections.emptyList())
					.forEach(propertySource -> composite.addFirstPropertySource(
							new MapPropertySource(propertySource.getName(), (Map<String, Object>) propertySource
									.getSource())));
		}
		return composite;
	}


	// TODO: 2020/6/6 retryable?
	private PropertySources getRemoteConfigPropertySources() {
		try {
			String endpoint = miniConfigClientProperties.getEndpoint();
			while (endpoint.endsWith("/")) {
				endpoint = endpoint.substring(0, endpoint.length() - 1);
			}
			String url = String.format("%s/get?namespace=%s&configNames=%s", endpoint,
					miniConfigClientProperties.getNamespace(), miniConfigClientProperties.getConfigNames());
			PropertySources result = restTemplate.getForObject(url, PropertySources.class);
			log.info("Get remote config {} from {}", url, result);
			return result;
		}
		catch (Exception e) {
			if (miniConfigClientProperties.isFailFast()) {
				throw new IllegalStateException("failed to get config from " + miniConfigClientProperties
						.getEndpoint(), e);
			}
			else {
				log.warn("failed to get config from {}", miniConfigClientProperties.getEndpoint());
				return null;
			}
		}
	}
}
