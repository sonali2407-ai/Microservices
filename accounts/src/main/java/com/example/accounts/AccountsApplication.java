package com.example.accounts;

import com.example.accounts.audit.AuditAwareImpl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef ="auditAwareImpl") // this is use to tell spring boot please spring data JPA auditing
@OpenAPIDefinition(info=@Info(title="Account Microservice RestApi Documentation",
description = "Bank Account microservices REST API Documentation",
version = "v1",
contact=@Contact(name="Sonali to reach out in case of documentation issue"),
		license=@License(
				name="Apache 2.0",
				url="https"
		)
))// To do documentation
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}

// to check documentation http://localhost:8080/swagger-ui