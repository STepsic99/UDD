package com.testforelastic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@SpringBootApplication
public class TestForElasticApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestForElasticApplication.class, args);
	}

}
