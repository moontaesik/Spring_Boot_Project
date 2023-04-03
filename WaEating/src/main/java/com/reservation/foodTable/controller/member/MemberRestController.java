package com.reservation.foodTable.controller.member;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.foodTable.Service.MemberService;
import com.reservation.foodTable.entity.Member;

@RestController
public class MemberRestController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/member/check/condition")
	public String checkIdDuplicate(@Param("id") Integer id, @RequestParam("userId") String userId,
			@RequestParam("phone") String phonenum) {
		boolean userid = memberService.isuserIdUnique(id, userId);
		boolean phone = memberService.isphoneUnique(id, phonenum);

		if (userid == true && phone == true) {
			return "OK";
		} else if (userid == false && phone == true) {
			return "IdDuplicated";
		} else if (userid == true && phone == false) {
			return "PhoneDuplicated";
		} else {
			return "Duplicated";
		}
	}

	// 휴대전화 번호로 null인지 확인
	@PostMapping("/login/check_idExist1") // 입력한 name이랑 phone번호로 ID찾기
	public String checkIdExist1(@RequestParam("phone") String phonenum) {
		return memberService.isNamePhonefind1(phonenum);
	}

	// 휴대전화 번호로 null이 아닌 것을 확인하고 이름 + 번호로 ID 찾기
	@PostMapping("/login/check_idExist2") // 입력한 name이랑 phone번호로 ID찾기
	public String checkIdExist2(@RequestParam("name") String name, @RequestParam("phone") String phonenum) {
		return memberService.isNamePhonefind2(name, phonenum);
	}

	@PostMapping("/login/findPassword")
	public String findPassword(@RequestParam("userId") String id, @RequestParam("phone") String phone) {
		return memberService.isuserIdNamePhoneExist(id, phone);
	}

	// 태식 - 회원 수정 하기 전에 password가 맞아야만 수정하는 창이 보임
	@PostMapping("/member/check/password")
	public String checkPassword(@RequestParam("id") Integer id, @RequestParam("password") String password) {
		Member member = memberService.findById(id);
		boolean matches = passwordEncoder.matches(password, member.getPassword());
		// 해쉬값으로 변한 패스워드를 encoder에서 비교해줌

		if (matches) {
			return "pass";
		} else {
			return "false";
		}
	}

	@PostMapping("/member/myInfo")
	public String[] myInfo(Principal p) {

		String[] userInfo = new String[2];

		Member member = memberService.findByUserId(p.getName());

		userInfo[0] = member.getName();
		userInfo[1] = member.getPhone();
		return userInfo;
	}
}
