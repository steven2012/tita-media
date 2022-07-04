package co.com.tita.platform.infrastructure.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = { "co.com.tita.platform.infrastructure", "co.com.tita.platform.modules",
"co.com.tita.platform.crosscutting" })
@EnableSwagger2
public class TitaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TitaApplication.class, args);
	}
}
