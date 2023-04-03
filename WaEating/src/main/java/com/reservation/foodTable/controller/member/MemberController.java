package com.reservation.foodTable.controller.member;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reservation.foodTable.Service.AreaService;
import com.reservation.foodTable.Service.MemberService;
import com.reservation.foodTable.dto.KakaOUserProfile;
import com.reservation.foodTable.dto.OAuthToken;
import com.reservation.foodTable.entity.Member;
import com.reservation.foodTable.enumClass.Role;
import com.reservation.foodTable.repository.CategoryRepository;

// 회원 관련 컨트롤러 
// 회원 가입 수정 탈퇴
// 비밀번호 찾기 아이디 찾기
// 
@Controller
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;
	private final CategoryRepository categoryRepository;
	private final AreaService areaService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	public MemberController(MemberService memberService, CategoryRepository categoryRepository,
			AreaService areaService) {
		super();
		this.memberService = memberService;
		this.categoryRepository = categoryRepository;
		this.areaService = areaService;
	}

	/*
	 * 3월 10일 영수 태식 1. public String loginPage() 2. public String signUp(Model
	 * theModel) 3. public String signUpSave(Member member, Model theModel) 4.
	 * public String findId(Model theModel) 5. public String findPw(Model theModel)
	 */

	@GetMapping("/join") // 첫 회원가입창 가는곳
	public String signUp(Model theModel) {
		Member member = new Member();
		theModel.addAttribute("member", member);
		theModel.addAttribute("pageTitle", "Create New Member");

		return "createmember";
	}

	@PostMapping("/save") // 첫 회원가입창 입력후 DB에 저장(첫 회원은 기본값으로 ROLE_USER 지정)
	public String signUpSave(Member member, Model theModel) {
		member.setRole(Role.ROLE_USER);
		memberService.save(member);
		theModel.addAttribute("categories", categoryRepository.findAll());
		theModel.addAttribute("parentAreas", areaService.getAreaParent());
		theModel.addAttribute("name", member.getName());
		theModel.addAttribute("id", member.getId());
		return "createmember_interest_area";
	}

	@PostMapping("/save/interest")
	@ResponseBody// 첫 회원가입창 입력후 DB에 저장(첫 회원은 기본값으로 ROLE_USER 지정)
	public String signUpSave2(@RequestBody Map<String,Object> data) {
		System.out.println(data);
	
		List<Integer> areaIds = (List<Integer>)data.get("areaIds");
		List<Integer> categoryIds = (List<Integer>)data.get("categoryIds");
		Integer id = (Integer)data.get("id");
		memberService.addInterest(id,areaIds,categoryIds);

		return "Good";
	}

	@GetMapping("/find/id") // ID 찾으러 가는곳
	public String findId(Model theModel) {
		return "find_id";
	}

	@GetMapping("/find/pw") // Password 찾으러 가는곳
	public String findPw(Model theModel) {
		return "find_pw";
	}
	
	@SuppressWarnings("null")
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code,RedirectAttributes attributes,Model model) {	
		OAuthToken oAuthToken = memberService.getToken(code);
		KakaOUserProfile userProfile = memberService.getUserProfile(oAuthToken);
		Member memberSearch = memberService.findByUserId(userProfile.getKakao_account().getEmail());
		if(memberSearch.getId() == null) {
			memberSearch.setName(userProfile.getProperties().getNickname());
			memberSearch.setPassword("123456789");
			memberSearch.setPhone(UUID.randomUUID().toString());
			memberSearch.setUserId(userProfile.getKakao_account().getEmail());
			memberSearch.setRole(Role.ROLE_USER);
			
			memberService.save(memberSearch);
			model.addAttribute("categories", categoryRepository.findAll());
			model.addAttribute("parentAreas", areaService.getAreaParent());
			model.addAttribute("name", memberSearch.getName());
			model.addAttribute("id", memberSearch.getId());
			
			return "createmember_interest_area";
		}
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(memberSearch.getUserId(),"123456789"));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("이메일::"+userProfile.getKakao_account().getEmail());
		System.out.println("이름:"+userProfile.getProperties().getNickname());
		return "redirect:/";
		
	}
}
