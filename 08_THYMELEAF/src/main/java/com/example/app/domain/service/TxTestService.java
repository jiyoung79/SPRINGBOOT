package com.example.app.domain.service;

import com.example.app.domain.mapper.MemoMapper;
import com.example.app.domain.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TxTestService {

    @Autowired
    private MemoMapper memoMapper;

    @Autowired
    private BookRepository bookRepository;

    @Transactional(rollbackFor=Exception.class,transactionManager = "dataSourceTransactionManager")
    public void txMapper(){
        log.info("TxTestService txMapper() invoke..");
        memoMapper.SelectAll();
    }

    @Transactional(rollbackFor=Exception.class,transactionManager = "jpaTransactionManager")
    public void txRepository(){
        log.info("TxTestService txRepository() invoke..");
        bookRepository.findAll();
    }

}
