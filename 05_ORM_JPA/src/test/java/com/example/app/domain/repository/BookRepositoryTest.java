package com.example.app.domain.repository;

import com.example.app.domain.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;


    @Test
    public void SaveRepo(){
        //System.out.println(bookRepository);
        assertThat(bookRepository).isNotNull();    //NULL이 아니면 테스트통과
        Book book = Book.builder()
                .bookCode(1117L)
                .bookName("JAVA의정석")
                .publisher("00미디어")
                .isbn("1111-2222")
                .build();
        assertThat(book).isNotNull();

        //INSERT
        Book result =  bookRepository.save(book);
        assertThat(result).isEqualTo(book);
    }

    @Test
    public void UpdateRepo(){
        Book book = Book.builder()
                .bookCode(1117L)
                .bookName("코드로 배우는 스프링부트")
                .publisher("와우미디어")
                .isbn("1111-2222")
                .build();
        bookRepository.save(book);
    }
    @Test
    public void DeleteRepo(){
        bookRepository.deleteById(1117L);
    }

    @Test
    public void SelectRepo(){
        List<Book> list =  bookRepository.findAll();
        list.forEach(dto->{
            System.out.println(dto);
        });
    }

    @Test
    public void SelectOneRepo(){
        Optional<Book> result =  bookRepository.findById(3L);
        Book book =  result.get();
        System.out.println(book);
    }

    // 함수추가해서 확인
    @Test
    public void SelectByBookName(){
        List<Book> list =  bookRepository.findByBookName("이것이리눅스다");
        list.forEach(dto->{
            System.out.println(dto);
        });
    }






}