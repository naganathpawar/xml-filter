package com.mef.filter.main.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author naganathpawar
 *
 */
@PropertySource("classpath:application.properties")
@ConfigurationProperties
public class ConfigProperties {
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

}
