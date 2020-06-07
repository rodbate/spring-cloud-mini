package com.github.rodbate.cloud.mini.config.common;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author rodbate
 * @since 2020/5/31
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PropertySource {
	private String name;
	private Map<?, ?> source;
}
