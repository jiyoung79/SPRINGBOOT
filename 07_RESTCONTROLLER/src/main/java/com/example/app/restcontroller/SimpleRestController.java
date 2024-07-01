package com.example.app.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.domain.dto.MemoDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/rest")
public class SimpleRestController {
	
	@GetMapping(value="/getText",produces=MediaType.TEXT_PLAIN_VALUE)
	public String rest01() {
		log.info("GET /rest/getText...");
		return "HELLO WORLD";
	}
	
	@GetMapping(value="/getJson",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public MemoDto Rest02() {
		log.info("GET /rest/getJson...");
		return new MemoDto(1,"HAHA");
	}
	
	@GetMapping(value="/getXml",produces=MediaType.APPLICATION_XML_VALUE)
	public MemoDto Rest03() {
		log.info("GET /rest/getXml...");
		return new MemoDto(1,"HAHA");
	}	

	@GetMapping(value="/getXmlList",produces=MediaType.APPLICATION_XML_VALUE)
	public List<MemoDto> Rest04() {
		log.info("GET /rest/getXmlList...");
		List<MemoDto> list = new ArrayList();
		for(int i=0;i<40;i++) {
			list.add(new MemoDto(i,"memo"+i));
		}
		return list;
	}
	
	@GetMapping(value="/getXmlList2/{show}",produces=MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity< List<MemoDto> > Rest05(@PathVariable Boolean show) {
		log.info("GET /rest/getXmlList2...");
		if(show!=null&&show==true) {
			List<MemoDto> list = new ArrayList();
			for(int i=0;i<40;i++) {
				list.add(new MemoDto(i,"memo"+i));
			}	
			return new ResponseEntity(list,HttpStatus.OK);
		}
		return new ResponseEntity(null,HttpStatus.BAD_REQUEST);
	}
}






