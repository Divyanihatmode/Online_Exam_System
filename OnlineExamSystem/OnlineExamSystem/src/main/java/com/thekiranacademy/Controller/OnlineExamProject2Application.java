package com.thekiranacademy.Controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EntityScan("com")
@ComponentScan("com")
public class OnlineExamProject2Application {
	public static void main(String[] args) {
		SpringApplication.run(OnlineExamProject2Application.class, args);
	}

}
