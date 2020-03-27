package com.hackpro.authserver.bean;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.hackpro.authserver.security.CassandraTokenStore;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Configuration
public class BeanConfiguration {

	@Bean(name = "passwordEncoder")
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	public TokenStore tokenStore() {
		return new CassandraTokenStore();
	}

	@Bean
	public AuthenticationKeyGenerator getAuthenticationKeyGenerator() {
		return new DefaultAuthenticationKeyGenerator();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}