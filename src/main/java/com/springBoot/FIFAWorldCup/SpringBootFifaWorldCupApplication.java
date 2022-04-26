package com.springBoot.FIFAWorldCup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import service.VoetbalServiceImpl;
import validator.AankoopValidation;

@SpringBootApplication
public class SpringBootFifaWorldCupApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFifaWorldCupApplication.class, args);
	}
	
	//css 
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("resources/css/");
	}
	
	//resource bundles
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource rbms = new ResourceBundleMessageSource();
		rbms.setBasename("messages"); //default locale is messages.properties
		return rbms;
	}
	
	@Bean
	public VoetbalServiceImpl voetbalServiceImpl() {
		return new VoetbalServiceImpl();
	}
	
	@Bean
	public AankoopValidation aankoopValidation() {
		return new AankoopValidation();
	}

}
