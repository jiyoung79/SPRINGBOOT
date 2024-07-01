package com.example.app.controller;

import com.example.app.domain.dto.MemoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/th")
public class ThymeleafTestController {

    @GetMapping("/test1")
    public void t1(Model model){
        log.info("GET /th/test1");
        model.addAttribute("name", "hong");
        model.addAttribute("memo", new MemoDto(1, "TEXT"));
        model.addAttribute("isAuth", true);

        List<MemoDto> list = new ArrayList();
        for(int i=1;i<=10;i++)
            list.add(new MemoDto(i, "MEMO"+i));
        model.addAttribute("list", list);
    }

    @GetMapping("/test2")
    public void t2() {
        log.info("GET /th/test2");
    }
}
