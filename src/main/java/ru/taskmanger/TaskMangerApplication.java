package ru.taskmanger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import ru.taskmanger.repository.jdbc.*;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class TaskMangerApplication {
	private static final Logger log = LoggerFactory.getLogger(TaskMangerApplication.class);

	private final JdbcUserDao jdbcUserDao;
	private final JdbcProjectDao jdbcProjectDao;
	private final JdbcColumnDao jdbcColumnDao;
	private final JdbcTaskListDao jdbcTaskListDao;
	private final JdbcTaskDao jdbcTaskDao;

	public TaskMangerApplication(JdbcUserDao jdbcUserDao,
								 JdbcProjectDao jdbcProjectDao,
								 JdbcColumnDao jdbcColumnDao,
								 JdbcTaskListDao jdbcTaskListDao,
								 JdbcTaskDao jdbcTaskDao) {
		this.jdbcUserDao = jdbcUserDao;
		this.jdbcProjectDao = jdbcProjectDao;
		this.jdbcColumnDao = jdbcColumnDao;
		this.jdbcTaskListDao = jdbcTaskListDao;
		this.jdbcTaskDao = jdbcTaskDao;
	}

	public static void main(String[] args) {
		SpringApplication.run(TaskMangerApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo() {
		return (args) -> {

		};
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
		//corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "File-Name"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
}
