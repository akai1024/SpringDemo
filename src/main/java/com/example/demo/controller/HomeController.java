package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

//	@Autowired
//	private AuthenticationManagerBuilder auth;

	@RequestMapping("/")
	public String home() {
		return "hello";
	}

//	@RequestMapping("/register")
//	public String register(@RequestParam("acc") String acc, @RequestParam("pwd") String pwd) throws Exception {
//		auth.inMemoryAuthentication().withUser(acc).password("{noop}" + pwd).roles("USER");
//		return "ok";
//	}

	
}
