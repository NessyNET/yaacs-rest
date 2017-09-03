package com.nessynet.yaacs.configuration;

import com.nessynet.yaacs.service.ann.AnnJooxParser;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ANNConfiguration {

	@Bean
	public RestTemplate annRestTemplate(RestTemplateBuilder builder, ANNConfigurationProperties props) {
		return builder.build();
	}

	@Bean
	public AnnJooxParser annJooxParser() {
		return new AnnJooxParser();
	}
}
