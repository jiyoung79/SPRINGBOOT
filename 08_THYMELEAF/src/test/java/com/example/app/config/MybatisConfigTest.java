package com.example.app.config;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MybatisConfigTest {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private SqlSession sqlSession;


    @Test
    public void t1(){
        assertNotNull(sqlSessionFactory);
    }
    @Test
    public void t2(){
        assertNotNull(sqlSession);

    }


}