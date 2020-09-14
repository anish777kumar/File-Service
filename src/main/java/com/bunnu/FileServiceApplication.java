package com.bunnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class FileServiceApplication {

	@Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.bunnu.controllers"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfo("File-Service Documentation", 
                							  "uload , download , delete file ",
                							  "1.0", 
                							  "NA", 
                							  "Anish/Bunnu", 
                							  "NA", 
                							  "-NA-")
                		         );
    }

	public static void main(String[] args) {
		SpringApplication.run(FileServiceApplication.class, args);
	}

}
