package com.augustoakuma.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.augustoakuma.cursomc.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instanciateDatabase() throws ParseException {
		
		if(! strategy.equals("create")) {
			return false;
		}
		
		dbService.instanciateTestDatabase();
		return true;
	}

}
