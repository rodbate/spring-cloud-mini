package com.github.rodbate.cloud.mini.config.server.exception;

/**
 *
 * @author rodbate
 * @since 2020/6/6
 */
public class ConfigWebException extends RuntimeException {
	public ConfigWebException(String message) {
		super(message);
	}

	public ConfigWebException(String message, Throwable cause) {
		super(message, cause);
	}
}
