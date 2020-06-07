package com.github.rodbate.cloud.mini.config.client;

import java.nio.charset.StandardCharsets;

import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

/**
 *
 * @author rodbate
 * @since 2020/5/31
 */
public abstract class ConfigUtils {


	public static String buildAuthorizationHeader(String accessKey, String secretKey) {
		//simple implementation
		if (!StringUtils.hasText(accessKey) || !StringUtils.hasText(secretKey)) {
			return "";
		}
		return Base64Utils.encodeToString((accessKey + " = " + secretKey).getBytes(StandardCharsets.UTF_8));
	}
}
