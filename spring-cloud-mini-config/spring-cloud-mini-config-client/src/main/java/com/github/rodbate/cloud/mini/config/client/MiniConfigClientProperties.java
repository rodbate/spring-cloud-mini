package com.github.rodbate.cloud.mini.config.client;

import java.time.Duration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author rodbate
 * @since 2020/5/31
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = MiniConfigClientProperties.PREFIX)
public class MiniConfigClientProperties {

	public static final String PREFIX = "spring.cloud.mini.config";

	private static final String DEFAULT_ENDPOINT = "http://localhost:8888/config";
	private static final String DEFAULT_NAMESPACE = "default";

	/**
	 * config client enable or not
	 */
	private boolean enabled;

	/**
	 * remote config server endpoint, like "http://localhost:8080/config"
	 *
	 * only support standalone config server currently
	 */
	private String endpoint = DEFAULT_ENDPOINT;

	/**
	 * access key for remote config server
	 */
	private String accessKey;

	/**
	 * secret key for remote config server
	 */
	private String secretKey;

	/**
	 * whether server fail fast when failed to fetch remote config
	 */
	private boolean failFast = false;

	/**
	 * config namespace
	 */
	private String namespace = DEFAULT_NAMESPACE;

	/**
	 * config name, support multiple configs separated by comma
	 */
	private String configNames;

	/**
	 * request connect timeout
	 */
	private Duration requestConnectTimeout = Duration.ofSeconds(5);

	/**
	 * request read timeout
	 */
	private Duration requestReadTimeout = Duration.ofSeconds(30);
}
