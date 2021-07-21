package com.cg.ppmtoolapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class PpmtoolapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PpmtoolapiApplication.class, args);
	}

}
