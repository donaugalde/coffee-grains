package com.coffeegrains.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
	scanBasePackages = {
		"com.coffeegrains.auth",
		"com.coffeegrains.serviceutils"
	}
)
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
