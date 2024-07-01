package com.example.app.domain.repository;

import com.example.app.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void t1(){

    }

    @Test
    public void jpqlTests1(){
        List<User> list =  userRepository.selectByRole("ROLE_USER");
        System.out.println(list);

    }
    @Test
    public void jpqlTests2(){
        List<User> list =  userRepository.selectAllByPassword("1234");
        System.out.println(list);
    }

    @Test
    public void jpqlTests3(){
        List<User> list =  userRepository.selectAllLikeUsername("mem");
        System.out.println(list);
    }
}