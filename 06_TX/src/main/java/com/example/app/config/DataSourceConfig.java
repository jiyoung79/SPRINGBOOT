package com.example.app.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {

//    @Bean
//    public DataSource dataSource1() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/bookdb");
//        dataSource.setUsername("root");
//        dataSource.setPassword("1234");
//        return dataSource;
//    }

    @Bean
    public HikariDataSource dataSource2() {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/bookdb");
        dataSource.setUsername("root");
        dataSource.setPassword("1234");
        return dataSource;
    }


}