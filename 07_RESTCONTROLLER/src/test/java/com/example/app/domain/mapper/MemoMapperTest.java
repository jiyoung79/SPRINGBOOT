package com.example.app.domain.mapper;

import com.example.app.domain.dto.MemoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoMapperTest {

    @Autowired
    private MemoMapper memoMapper;

    @Test
    void insert() {
        assertNotNull(memoMapper);
        memoMapper.Insert(new MemoDto(null,"MEMO"));
    }
    @Test
    void t2(){
        memoMapper.InsertXML(new MemoDto(null,"MEMO2"));
    }

}