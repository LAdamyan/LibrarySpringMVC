package com.epam.libraryspringmvc.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:h2-config.properties")
public class DBConnectionProvider {

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driverClass}")
    private String dbDriverName;

    @Bean("dbConnectionProvider")
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .url(dbUrl)
                .username(username)
                .password(password)
                .driverClassName(dbDriverName)
                .build();
    }
}
