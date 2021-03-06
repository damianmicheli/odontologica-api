package ar.com.dami.odontologica;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RestController
public class OdontologicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OdontologicaApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("https://odontologica.dami.com.ar","http://127.0.0.1:5500")
						.allowedMethods("PUT", "DELETE", "GET", "POST");
			}
		};
	}
}


