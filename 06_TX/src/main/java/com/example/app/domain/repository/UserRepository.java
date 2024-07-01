package com.example.app.domain.repository;


import com.example.app.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    @Query("SELECT u FROM User u WHERE u.role=?1")
    List<User> selectByRole(String role);

    @Query("SELECT u FROM User u WHERE u.password=:password")
    List<User> selectAllByPassword(@Param("password") String pw);

    @Query("SELECT u FROM User u WHERE u.password=?1 AND u.role=?2")
    List<User> selectAllByPasswordAndRole(String password,String role);

    @Query("SELECT u FROM User u WHERE u.username LIKE CONCAT('%',:username,'%')")
    List<User> selectAllLikeUsername(@Param("username") String username);

}
