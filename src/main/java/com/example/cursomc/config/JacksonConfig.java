package com.example.cursomc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.example.cursomc.domain.PaymentBankTransfer;
import com.example.cursomc.domain.PaymentCreditCard;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfig {
// https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(PaymentCreditCard.class);
				objectMapper.registerSubtypes(PaymentBankTransfer.class);
				super.configure(objectMapper);
			}
		};
		return builder;
	}
}