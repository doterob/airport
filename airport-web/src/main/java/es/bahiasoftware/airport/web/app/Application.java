package es.bahiasoftware.airport.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {

	private Application() {}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
