package com.github.rodbate.cloud.mini.config.common;

import java.util.List;

import lombok.Getter;
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
public class PropertySources {

	private String namespace;

	private List<PropertySource> propertySourceList;

}
