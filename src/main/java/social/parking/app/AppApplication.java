package social.parking.app;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import social.parking.app.config.SwaggerConfiguration;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@Import(SwaggerConfiguration.class)
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
