package com.example.PruebaTecnica4Agency;




import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class PruebaTecnica4AgencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaTecnica4AgencyApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info()
				.title(" Agency API")
				.version("1.0.0")
				.description("API for a  Agency."));
	}



}
