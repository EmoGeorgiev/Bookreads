package com.Bookreads;

import com.Bookreads.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class BookreadsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookreadsApplication.class, args);
	}

}
