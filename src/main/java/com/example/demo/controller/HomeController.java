package com.example.demo.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

//	@Autowired
//	private AuthenticationManagerBuilder auth;

	@RequestMapping("/")
	public String home() {
		if(logger.isInfoEnabled()) {
			logger.info(new Date().toString());
		}
		
		return "hello";
	}

//	@RequestMapping("/register")
//	public String register(@RequestParam("acc") String acc, @RequestParam("pwd") String pwd) throws Exception {
//		auth.inMemoryAuthentication().withUser(acc).password("{noop}" + pwd).roles("USER");
//		return "ok";
//	}

	
}
