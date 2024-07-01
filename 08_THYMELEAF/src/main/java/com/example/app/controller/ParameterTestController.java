package com.example.app.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.dto.PersonDto;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/test")
public class ParameterTestController {

    //GET
    //@RequestMapping(value = "/p01" ,method=RequestMethod.GET)
    @GetMapping("/p01")
    public void p1(@RequestParam(required = true,name="name") String name) {
        log.info("GET /test/p01..." + name);
    }

    @GetMapping("/p02")
    public void p2(@RequestParam(required = false,name="username") String name) {
        log.info("GET /test/p02..." + name);
    }

    //POSTMAN TESTS
    //MOCK TESTS
    @RequestMapping(value="/p03",method=RequestMethod.POST)
    public void p3(@RequestParam("username") String username) {
        log.info("POST /test/p03..."+username);
    }

    // @RequestParam 	: 동기요청 방식 기본 / form 기반 요청 받기
    // @RequestBody 	: 비동기요청 방식 기본 /  form / json 등 받기
    @RequestMapping(value="/p04",method=RequestMethod.POST)
    public void p4(@RequestBody String username) {
        log.info("POST /test/p04..."+username);
    }

    @RequestMapping(value="/p05",method=RequestMethod.GET)
    public void p5(@RequestParam(required=false,defaultValue="홍길동") String username) {
        log.info("GET /test/p05..."+username);
    }

    @RequestMapping(value="/p06",method=RequestMethod.GET)
    public void p6(String name, int age , String addr) {
        log.info("GET /test/p06..."+name+" " + age + " " + addr);
    }

    @RequestMapping(value="/p07",method=RequestMethod.GET)
    public void p7(@ModelAttribute PersonDto dto) {
        log.info("GET /test/p07..."+dto);
    }

    @RequestMapping(value="/p08/{username}/{age}/{addr}",method=RequestMethod.GET)
    public void p8(
            @PathVariable("username") String name,
            @PathVariable int age ,
            @PathVariable String addr) {
        log.info("GET /test/p08..."+name+" " + age + " " + addr);
    }

    @RequestMapping(value="/p09/{name}/{age}/{addr}",method=RequestMethod.GET)
    public void p9(PersonDto dto) {
        log.info("GET /test/p09..."+dto);
    }

    @GetMapping("/page1")	//test/page1
    public void page1(PersonDto dto ,Model model) {
        log.info("GET /test/page1..."+dto);
        model.addAttribute("TEST1","TEST1_VALUE");
        model.addAttribute("personDto",dto);
    }
    @GetMapping("/page2")
    public String page2(PersonDto dto ,Model model) {
        log.info("GET /test/page2..."+dto);
        model.addAttribute("TEST1","PAGE2_VALUE");
        model.addAttribute("personDto",dto);
        return "test/page1";
    }
    @GetMapping("/page3/{name}/{age}/{addr}")
    public String page3(PersonDto dto ,Model model) {
        log.info("GET /test/page3..."+dto);
        model.addAttribute("TEST1","PAGE2_VALUE");
        model.addAttribute("personDto",dto);
        return "test/page1";
    }

    @GetMapping("/page4/{name}/{age}/{addr}")
    public ModelAndView page4(PersonDto dto) {
        log.info("GET /test/page4..."+dto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("personDto",dto);
        modelAndView.setViewName("test/page1");
        return modelAndView;
    }

    //FORWARD
    @GetMapping("/forward1")
    public String f1(Model model) {
        log.info("GET /test/forward1...");
        model.addAttribute("forward1","forward1Value");
        return "forward:/test/forward2";
    }
    @GetMapping("/forward2")
    public String f2(Model model) {
        log.info("GET /test/forward2...");
        model.addAttribute("forward2","forward2Value");
        return "forward:/test/forward3";
    }
    @GetMapping("/forward3")
    public void f3(Model model) {
        log.info("GET /test/forward3...");
        model.addAttribute("forward3","forward3Value");
    }

    //Redirect
    @GetMapping("/redirect1")
    public String r1(RedirectAttributes redirectAttributes) {
        log.info("GET /test/redirect1...");
        redirectAttributes.addAttribute("redirect1","redirect1Value"); //URL 의 쿼리스트링으로 전달
        redirectAttributes.addFlashAttribute("r1", "r1Value");			//세션에 저장
        return "redirect:/test/redirect2";
    }
    @GetMapping("/redirect2")
    public String r2(String redirect1, @ModelAttribute("r1") String r1, RedirectAttributes redirectAttributes) {
        log.info("GET /test/redirect2..."+redirect1 + " r1 : " + r1);
        redirectAttributes.addAttribute("redirect1",redirect1);
        redirectAttributes.addAttribute("redirect2","redirect2Value");

        redirectAttributes.addFlashAttribute("r1", r1);
        redirectAttributes.addFlashAttribute("r2", "r2Value");
        return "redirect:/test/redirect3";
    }
    @GetMapping("/redirect3")
    public String r3(String redirect1,String redirect2,  @ModelAttribute("r1")  String r1, @ModelAttribute("r2")  String r2, RedirectAttributes redirectAttributes) {
        log.info("GET /test/redirect3...r1 : " + r1 + " r2 : " + r2);
        redirectAttributes.addAttribute("redirect1",redirect1);
        redirectAttributes.addAttribute("redirect2",redirect2);
        redirectAttributes.addAttribute("redirect3","redirect3Value");

        redirectAttributes.addFlashAttribute("r1", r1);
        redirectAttributes.addFlashAttribute("r2", r2);
        redirectAttributes.addFlashAttribute("r3", "r3Value");
        return "redirect:/test/redirectResult";
    }
    @GetMapping("/redirectResult")
    public void redirectResult(String redirect1,String redirect2,String redirect3, Model model) {
        model.addAttribute("redirect1",redirect1);
        model.addAttribute("redirect2",redirect2);
        model.addAttribute("redirect3",redirect3);
    }

    //ServletCode 적용확인

    @GetMapping("/servlet_test")
    public void Servlet_test(HttpServletRequest req, HttpServletResponse resp) {
        log.info("GET /test/servlet_test..");
        log.info("request : " + req);
        log.info("response : " + resp);
        HttpSession session =  req.getSession();
        log.info("session : " + session);
    }


    @GetMapping("/join")
    public void join_get() {
        log.info("GET /test/join..");
    }

    @PostMapping("/join")
    public void join_post(PersonDto dto) {
        log.info("POST /test/join.." + dto);
    }

}









