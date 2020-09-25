package br.com.meta.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "br.com.meta.*")
@ComponentScan(basePackages = "br.com.meta.*")
@EntityScan(basePackages = "br.com.meta.*")
@EnableJpaRepositories(basePackages = "br.com.meta.repository")
@EnableJpaAuditing
public class App {

	public static void main(final String[] args) {
		SpringApplication.run(App.class, args);
	}
}
