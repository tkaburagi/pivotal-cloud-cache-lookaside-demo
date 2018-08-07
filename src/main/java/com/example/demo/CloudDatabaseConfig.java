package com.example.demo;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories
@Profile("cloud")
public class CloudDatabaseConfig extends AbstractCloudConfig {
    @Bean
    public DataSource dataSource() {
        DataSource dataSource = connectionFactory().dataSource();

        return dataSource;
    }
}
