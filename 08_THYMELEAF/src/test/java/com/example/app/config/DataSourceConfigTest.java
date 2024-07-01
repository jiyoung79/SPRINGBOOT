package com.example.app.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class DataSourceConfigTest {

    @Autowired
    private DataSource dataSource1;
    @Autowired
    private DataSource dataSource2;
    @Test
    public void t1() throws SQLException {
        System.out.println(dataSource1);
        System.out.println(dataSource2.getConnection());
    }

}





