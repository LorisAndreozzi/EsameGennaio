package com.example.PrUni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({ "controllers" })
@SpringBootApplication
public class ProggettoUniApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProggettoUniApplication.class, args);
	}

}
