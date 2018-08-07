package com.example.demo;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.config.annotation.EnableLogging;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.geode.config.annotation.EnableDurableClient;
import org.springframework.geode.config.annotation.UseMemberName;

@EnableDurableClient(id = "PccApiClient")
@EnableLogging(logLevel = "info")
@UseMemberName("PccApiClient")
@EnableGemfireRepositories(basePackages = "com.example.demo")
@ComponentScan(basePackages = "com.example.demo")
@Profile("cloud")
@Configuration
public class CloudCacheConfig {
}
