package com.github.rodbate.cloud.mini.config.client;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import static com.github.rodbate.cloud.mini.config.client.ConfigUtils.buildAuthorizationHeader;

/**
 *
 * @author rodbate
 * @since 2020/5/31
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MiniConfigClientProperties.class)
@ConditionalOnProperty(name = "spring.cloud.mini.config.enabled", matchIfMissing = true)
public class MiniConfigClientBootstrapConfiguration {
	public static final String MINI_CONFIG_CLIENT_REST_TEMPLATE_BEAN_NAME = "miniConfigClientRestTemplate";


	@Bean(MINI_CONFIG_CLIENT_REST_TEMPLATE_BEAN_NAME)
	@ConditionalOnMissingBean(name = MINI_CONFIG_CLIENT_REST_TEMPLATE_BEAN_NAME)
	public RestTemplate miniConfigClientRestTemplate(MiniConfigClientProperties configClientProperties) {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

		Objects.requireNonNull(configClientProperties
				.getRequestConnectTimeout(), "requestConnectTimeout require not null");
		Objects.requireNonNull(configClientProperties.getRequestReadTimeout(), "requestReadTimeout require not null");

		requestFactory.setConnectTimeout((int) configClientProperties.getRequestConnectTimeout().toMillis());
		requestFactory.setReadTimeout((int) configClientProperties.getRequestReadTimeout().toMillis());

		final RestTemplate template = new RestTemplate(requestFactory);
		template.setInterceptors(Collections
				.singletonList(new AuthorizationHeaderRequestInterceptor(configClientProperties)));
		return template;
	}

	@Bean
	@ConditionalOnMissingBean
	public MiniConfigPropertySourceLocator miniConfigPropertySourceLocator(@Qualifier(MINI_CONFIG_CLIENT_REST_TEMPLATE_BEAN_NAME) RestTemplate restTemplate, MiniConfigClientProperties miniConfigClientProperties) {
		return new MiniConfigPropertySourceLocator(restTemplate, miniConfigClientProperties);
	}

	private static class AuthorizationHeaderRequestInterceptor implements ClientHttpRequestInterceptor {

		private final MiniConfigClientProperties miniConfigClientProperties;

		private AuthorizationHeaderRequestInterceptor(MiniConfigClientProperties miniConfigClientProperties) {
			this.miniConfigClientProperties = miniConfigClientProperties;
		}

		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
			request.getHeaders().add(HttpHeaders.AUTHORIZATION, buildAuthorizationHeader(miniConfigClientProperties
					.getAccessKey(), miniConfigClientProperties.getSecretKey()));
			return execution.execute(request, body);
		}
	}
}
