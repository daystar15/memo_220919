package com.memo.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {

	/**
	 * 회원가입 화면
	 * @return
	 */
	@GetMapping("/sign_up_view")
	public String signUpView(Model model) {
		model.addAttribute("viewName", "user/signUp"); // 매번 바뀌는 컨텐츠(조각 페이지)
		return "template/layout";
	}
	
	/**
	 * 로그인 화면
	 * @return
	 */
	@GetMapping("/sign_in_view")
	public String signInView(Model model) {
		model.addAttribute("viewName", "user/signIn"); // 매번 바뀌는 컨텐츠(조각 페이지)
		return "template/layout";
	}
}
