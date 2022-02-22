package com.codex.machina.ex.homini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CodexMachinaExHominiApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(CodexMachinaExHominiApplication.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder(11);
	}
}
