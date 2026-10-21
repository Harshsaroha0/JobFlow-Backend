package com.jobflow.monolith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jobflow")
@EntityScan("com.jobflow")
@EnableJpaRepositories("com.jobflow")
@ComponentScan(
    basePackages = "com.jobflow",
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.REGEX,
        pattern = "com\\.jobflow\\..*\\.exception\\.GlobalExceptionHandler"
    )
)
public class JobFlowMonolithApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobFlowMonolithApplication.class, args);
    }
}