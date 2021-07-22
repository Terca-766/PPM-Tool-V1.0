package com.cg.ppmtoolapi.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cg.ppmtoolapi.domain.Backlog;
import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwagerConfig {	
	private final TypeResolver typeResolver;


	public SwagerConfig(final TypeResolver typeResolver) {
	    this.typeResolver = typeResolver;
	}
	
	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)		
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.cg.ppmtoolapi.web"))
				.paths(PathSelectors.regex("/api.*"))				
				.build()
				.apiInfo(metoInfo())
				.additionalModels(typeResolver.resolve(Backlog.class))
				.ignoredParameterTypes(Iterable.class);
	}

	private ApiInfo metoInfo() {
		// Customize the Swagger output
		ApiInfo apiInfo = new ApiInfo(
				"Project Portfolio Management API", 
				"PPM API Created under guideance of Pankaj Sharma ", 
				"1.0", 
				"Terms of Service", 
				new Contact("Prashant Sharma", "https://www.talent.capgemini.com/","prashant.g.sharma@capgemini.com"), 
				"CAPGEMINI Licence v.1.0", 
				"https://www.talent.capgemini.com/",
				new ArrayList<>());
		return apiInfo;
	}

}
