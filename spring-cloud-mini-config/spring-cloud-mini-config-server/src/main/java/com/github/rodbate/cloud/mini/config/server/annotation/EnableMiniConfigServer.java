package com.github.rodbate.cloud.mini.config.server.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.rodbate.cloud.mini.config.server.config.MiniConfigServerConfiguration;

import org.springframework.context.annotation.Import;

/**
 *
 * @author rodbate
 * @since 2020/6/6
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(MiniConfigServerConfiguration.class)
public @interface EnableMiniConfigServer {
}
