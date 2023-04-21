package br.com.register.part;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.com.register.part.infrastructure")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
