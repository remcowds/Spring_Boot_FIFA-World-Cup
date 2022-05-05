package com.springBoot.FIFAWorldCup;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().defaultSuccessUrl("/fifa", true);

		// alle urls onderscheppen en toegang instellen
		http.authorizeRequests().antMatchers("/*").hasRole("USER").and().authorizeRequests().antMatchers("/fifa/*")
				.hasRole("ADMIN").and().csrf();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication().withUser("user").password(encoder.encode("user")).roles("USER").and()
				.withUser("admin").password(encoder.encode("admin")).roles("USER", "ADMIN");
	}
}
