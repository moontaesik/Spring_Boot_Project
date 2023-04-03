package com.reservation.foodTable.controller.customerService;

import java.io.IOException;
import java.security.Principal;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reservation.foodTable.Service.CustomerSupportService;
import com.reservation.foodTable.Service.MemberService;
import com.reservation.foodTable.config.FileUploadUtil;
import com.reservation.foodTable.entity.Member;
import com.reservation.foodTable.entity.OneToOneInquiry;
import com.reservation.foodTable.enumClass.InquiryState;

@Controller
public class CustomerServiceController {

	private final CustomerSupportService customerSupportService;
	private final MemberService memberService;

	public CustomerServiceController(CustomerSupportService customerSupportService, MemberService memberService) {
	
		this.customerSupportService = customerSupportService;
		this.memberService = memberService;
	}
	
	
	@GetMapping("/customer-service/index")
	public String getFAQs(Model model,@PageableDefault(size=10,page=0,sort = "id", direction = Sort.Direction.DESC)Pageable pageable
			,@RequestParam(required = false, defaultValue = "") String keyword) {
		
		model.addAttribute("FAQ",customerSupportService.findFAQ(keyword,pageable));
		model.addAttribute("head",1);
		model.addAttribute("keyword", keyword);

		return "customer-service/index";
	}
	
	@GetMapping("/customer-service/noticeList")
	public String getNotices(Model model,@PageableDefault(size=10,page=0,sort = "id", direction = Sort.Direction.DESC)Pageable pageable
			,@RequestParam(required = false, defaultValue = "") String keyword
			,@RequestParam(name="target",required = false, defaultValue = "1")Integer target) {
		
		model.addAttribute("Notice",customerSupportService.getNoticeList(keyword,target,pageable));
		model.addAttribute("head",2);
		model.addAttribute("keyword", keyword);
		model.addAttribute("target", target);

		return "customer-service/notice";
	}
	
	@GetMapping("/customer-service/noticeList/{id}")
	public String getNotices(Model model,@PathVariable("id")Integer noticeId) {
		
		model.addAttribute("notice",customerSupportService.getNoticePageById(noticeId));
		model.addAttribute("head",2);

		return "customer-service/notice_detail";
	}
	
	@GetMapping("/customer-service/oneToOne")
	public String getOneToOneForm(Principal p,Model model,RedirectAttributes re) {
		
		if(p==null) {
			
			re.addFlashAttribute("message","로그인 후 이용가능한 서비스 입니다. 로그인하시겠습니까?");
			return "redirect:/customer-service/index";
		}
		model.addAttribute("classification",customerSupportService.getCategories());
		model.addAttribute("head",3);
		OneToOneInquiry one = new OneToOneInquiry();
		Member member = memberService.findByUserId(p.getName());
		one.setMember(member);
		model.addAttribute("one", one);
		return "customer-service/oneToOne";
	}
	
	@PostMapping("/customer-service/oneToOne/save")
	public String saveOneToOne(OneToOneInquiry one, @RequestParam("image") MultipartFile multipartFile) throws IOException {
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			one.setFilePath(fileName);
			one.setState(InquiryState.WAITING);
			OneToOneInquiry oneto = customerSupportService.save(one);
			
			String uploadDir = "onetoone-image/" + oneto.getId();
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}else {
				one.setFilePath(null);
			customerSupportService.save(one);
		}
		
		return "redirect:/";
	}
}
