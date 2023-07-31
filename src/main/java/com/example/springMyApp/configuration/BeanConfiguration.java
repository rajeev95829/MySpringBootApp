package com.example.springMyApp.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfiguration {

	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}
}
