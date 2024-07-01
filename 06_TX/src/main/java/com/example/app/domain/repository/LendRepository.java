package com.example.app.domain.repository;

import com.example.app.domain.entity.Book;
import com.example.app.domain.entity.Lend;
import com.example.app.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LendRepository extends JpaRepository<Lend,Long> {

    //명명법
    List<Lend> findByUser(User user);
    List<Lend> findByBook(Book book);
    List<Lend> findByRegDate(LocalDate regDate);
    List<Lend> findByReturnDate(LocalDate returnDate);

    //JPQL



}
