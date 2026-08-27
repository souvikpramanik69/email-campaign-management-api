package com.kasplo.email_campaign_management_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class DbConfig {


    private final String username;
    private final String url;
    private final String password;

    public  DbConfig(@Value("${spring.datasource.url}") String url,
                             @Value("${spring.datasource.username}") String username,
                             @Value("${spring.datasource.password}") String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }


    @Bean
    @Profile("dev")
    @ConfigurationProperties(prefix = "spring.datasource.*")
    public DataSource devDataSource(){
        return DataSourceBuilder.create().url(url).username(username).password(password).build();
    }

    @Bean
    @Profile("prod")
    @ConfigurationProperties(prefix = "spring.datasource.*")
    public DataSource prodDataSource(){
        return DataSourceBuilder.create().url(url).username(username).password(password).build();
    }


}

