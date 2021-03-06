package com.example.sp11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class Sp11ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(Sp11ZuulApplication.class, args);
	}

}
