package com.project.nasnav.rs.control;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class NasnavApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(NasnavApp.class, args);
	}

	@Bean
	public Docket NasnavApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.project.nasnav.rs.control"))
				.paths(PathSelectors.any())
		         .build()
		         .apiInfo(apiDetails());
	}
	
	

	private ApiInfo apiDetails() {
		   return new ApiInfoBuilder()
			         .title("Nasnav Rest APIs Documentation")
			         .description("")
			         .version("")
			         .license("")
			         .contact(new Contact("Sohaila Esawy","https://m.com/", "try@mail.com"))
			         .build();
	}
}
