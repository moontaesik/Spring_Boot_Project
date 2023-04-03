package com.reservation.foodTable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 로그인 관련 컨트롤러

@Controller
public class LoginController {

	@GetMapping("/login") // login url 창으로
	public String loginPage() {
		return "login";
	}
	
}
