package com.reservation.foodTable.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reservation.foodTable.Service.AreaService;
import com.reservation.foodTable.Service.MemberInterestAreaService;
import com.reservation.foodTable.Service.MemberInterestCategoryService;
import com.reservation.foodTable.Service.MemberService;
import com.reservation.foodTable.Service.OneToOneInquiryService;
import com.reservation.foodTable.Service.ReservationInfoService;
import com.reservation.foodTable.Service.ReservationOrderInfoService;
import com.reservation.foodTable.Service.ReviewService;
import com.reservation.foodTable.dto.OneToOneDTO;
import com.reservation.foodTable.dto.ReservationDTO;
import com.reservation.foodTable.dto.ReviewDTO;
import com.reservation.foodTable.embeddedType.Score;
import com.reservation.foodTable.entity.Member;
import com.reservation.foodTable.entity.MemberInterestArea;
import com.reservation.foodTable.entity.MemberInterestCategory;
import com.reservation.foodTable.entity.OneToOneInquiry;
import com.reservation.foodTable.entity.OneToOneInquiryComment;
import com.reservation.foodTable.entity.ReservationInfo;
import com.reservation.foodTable.entity.ReservationOrderInfo;
import com.reservation.foodTable.entity.Review;
import com.reservation.foodTable.repository.CategoryRepository;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

	private final MemberService memberService;
	private final ReservationInfoService reservationInfoService;
	private final ReviewService reviewService;
	private final MemberInterestCategoryService memberInterestCategoryService;
	private final MemberInterestAreaService memberInterestAreaService;
	private final ReservationOrderInfoService reservationOrderInfoService;
	private final OneToOneInquiryService oneToOneInquiryService;
	private final CategoryRepository categoryRepository;
	private final AreaService areaService;

	public MyPageController(MemberService memberService, ReservationInfoService reservationInfoService,
			ReviewService reviewService, MemberInterestCategoryService memberInterestCategoryService,
			MemberInterestAreaService memberInterestAreaService,
			ReservationOrderInfoService reservationOrderInfoService, OneToOneInquiryService oneToOneInquiryService,
			CategoryRepository categoryRepository, AreaService areaService) {
		this.memberService = memberService;
		this.reservationInfoService = reservationInfoService;
		this.reviewService = reviewService;
		this.memberInterestCategoryService = memberInterestCategoryService;
		this.memberInterestAreaService = memberInterestAreaService;
		this.reservationOrderInfoService = reservationOrderInfoService;
		this.oneToOneInquiryService = oneToOneInquiryService;
		this.categoryRepository = categoryRepository;
		this.areaService = areaService;
	}

	// 태식 - 내정보 보여주기 위한 창가기
	@GetMapping("")
	public String findMy(Authentication auth, Model theModel) {
		Member member = memberService.findByUserId(auth.getName());
		// 관심 카테고리
		List<MemberInterestCategory> memberInca = memberInterestCategoryService.findByMemberId(member.getId());
		// 관심 지역
		List<MemberInterestArea> memberInar = memberInterestAreaService.findByMemberId(member.getId());

		theModel.addAttribute("member", member);
		theModel.addAttribute("memberInca", memberInca);
		theModel.addAttribute("memberInar", memberInar);

		List<ReservationDTO> reservadto = reservationInfoService.afterInfo(member.getId());
		// 예약 목록에서 로그인한 유저의 예약정보를 가져옴 아직 안지난 예약만
		theModel.addAttribute("reservation", reservadto);

		return "mypage/home";
	}

	// 태식 - 예약목록을 페이징 처리해서 보여주는 곳
	@GetMapping("/reservation")
	public String listByPage(@RequestParam(value = "page", required = false, defaultValue = "1") int num,
			Model theModel, Authentication auth) {
		String sortField = "restaurantAvaTime"; // 예약한 시간으로 정렬을 해야함
		String sortDir = "desc"; // 시간 역순으로 가져와야 최근 예약부터 보임

		Member member = memberService.findByUserId(auth.getName()); // 로그인한 사람에 정보를 조회
		theModel.addAttribute("member", member);

		List<ReservationDTO> reservadto = reservationInfoService.afterInfo(member.getId());
		// 예약 목록에서 로그인한 유저의 예약정보를 가져옴 아직 안지난 예약만
		theModel.addAttribute("reservation", reservadto);

		Page<ReservationDTO> page = reservationInfoService.listByPage(num, sortField, sortDir, member.getId());
		// 페이징 처리하려고 정보를 넘겨줌, 예약목록을 가져오는 현재시간보다 지난 데이터들을 가져옴
		// num : 처음 1페이지, sortField : 무엇으로 정렬을 할건지, sortDir : 정렬 방법

		List<ReservationDTO> reserva = page.getContent(); // 페이징처리한걸 리스트로 넣어줌
		int totalPage = page.getTotalPages();
		int currentPage = page.getNumber() + 1;
		int pages = (int) Math.ceil(currentPage / 5.0);
		// 현제 페이지가 1 2 3 4 5 가 아니면 prevIsEnabled 해주면 된다.
		boolean prevIsEnabled = pages != 1;
		boolean nextIsEnabled = pages < (int) Math.ceil(totalPage / 5.0);

		List<Integer> pageList = new ArrayList<>();
		for (int i = (pages - 1) * 5 + 1; i < pages * 5 + 1 && i <= totalPage; i++) {
			pageList.add(i);
		}

		theModel.addAttribute("latereservation", reserva);

		theModel.addAttribute("currentPage", currentPage);
		theModel.addAttribute("totalPages", totalPage);
		theModel.addAttribute("prevIsEnabled", prevIsEnabled);
		theModel.addAttribute("nextIsEnabled", nextIsEnabled);
		theModel.addAttribute("pageList", pageList);

		theModel.addAttribute("sortField", sortField);
		theModel.addAttribute("sortDir", sortDir);

		return "mypage/myReservation";
	}

	// 태식 - 개인정보 수정 창으로 가기
	@GetMapping("/update")
	public String updateMember(Authentication auth, Model theModel) {
		Member member = memberService.findByUserId(auth.getName());
		theModel.addAttribute("member", member);

		return "mypage/myInfoModify";
	}

	// 태식 - 입력한 update내용 save하기
	@PostMapping("/save")
	public String saveMember(Member member, HttpSession session) {
		memberService.save(member);

		session.invalidate();
		return "redirect:/login";
	}

	// 태식 - 관심 지역, 카테고리 수정
	@GetMapping("/update/interest")
	public String updateInterest(Authentication auth, Model theModel) {
		Member member = memberService.findByUserId(auth.getName());

		theModel.addAttribute("myCategory", categoryRepository.findMyCategory(member));
		theModel.addAttribute("myArea", areaService.findMyArea(member));
		theModel.addAttribute("categories", categoryRepository.findAll());
		theModel.addAttribute("parentAreas", areaService.getAreaParent());
		theModel.addAttribute("name", member.getName());
		theModel.addAttribute("id", member.getId());

		return "mypage/myCategory_Area_Modify";
	}

	// 태식 - 관심 지역, 카테고리 저장
	@PostMapping("/save/interest")
	@ResponseBody // 첫 회원가입창 입력후 DB에 저장(첫 회원은 기본값으로 ROLE_USER 지정)
	public String signUpSave2(@RequestBody Map<String, Object> data) {
		System.out.println(data);

		List<Integer> areaIds = (List<Integer>) data.get("areaIds");
		List<Integer> categoryIds = (List<Integer>) data.get("categoryIds");
		Integer id = (Integer) data.get("id");
		memberService.deleteByMemberId(id);
		memberService.addInterest(id, areaIds, categoryIds);

		return "Good";
	}

	// 태식 - 리뷰 처음 작성하기 위한 창가기
	@GetMapping("/review/new/{reservationid}")
	public String reviewNew(@PathVariable("reservationid") Integer reservationid, Model theModel) {
		// url로 예약정보에 id값을 가져와서 그 id를 통해 조회 : 뷰에서 예약 정보에 담겨있는 내용을 보여주기 위함
		ReservationInfo reserva = reservationInfoService.findById(reservationid);
		theModel.addAttribute("reservation", reserva);

		List<ReservationOrderInfo> orderInfo = reservationOrderInfoService.findByReservationInfoId(reservationid);
		theModel.addAttribute("orderInfo", orderInfo);

		// 리뷰 객체를 생성하여 review 예약컬럼에다가 예약정보 set해주고 뷰단으로 보냄
		Review review = new Review();
		review.setReservationInfo(reserva);
		theModel.addAttribute("review", review);

		// dto를 통해서 별점 관련된 객체만 보냄
		ReviewDTO scoredto = new ReviewDTO();
		theModel.addAttribute("scoredto", scoredto);

		return "review";
	}

	// 태식 - 리뷰 업데이트를 가기 위한 페이지
	@GetMapping("/review/update/{reservationid}")
	public String reviewUpdate(@PathVariable("reservationid") Integer reservationid, Model theModel) {

		ReservationInfo reserva = reservationInfoService.findById(reservationid);
		theModel.addAttribute("reservation", reserva);

		Review review = reviewService.findByReservationInfoId(reservationid);
		theModel.addAttribute("review", review);

		ReviewDTO scoredto = new ReviewDTO(review.getScore());
		theModel.addAttribute("scoredto", scoredto);

		return "review";
	}

	// 태식 - 작성한 리뷰를 저장하기 위한 페이지
	@PostMapping("/review/save")
	public String reviewSave(Review review, Model theModel, ReviewDTO scoredto) {
		// 입력한 별점을 review에 담아서 넘겨줌
		float price2 = (float) (Math.round(scoredto.getPriceScore() * 100) / 100.0);
		float service2 = (float) (Math.round(scoredto.getServiceScore() * 100) / 100.0);
		float taste2 = (float) (Math.round(scoredto.getTasteScore() * 100) / 100.0);
		Score score = new Score(taste2, service2, price2);
		review.setScore(score);
		reviewService.save(review);

		// 리뷰를 작성하고 별점이 입력되면 restaurant에 있는 별점도 바껴야 함, 예약한 식당 id를 넘겨줌
		Integer reser_id = review.getReservationInfo().getRestaurant().getId();
		reservationInfoService.findCore(reser_id);

		return "redirect:/mypage/reservation";
	}

	// 태식 - 리뷰삭제
	@GetMapping("/review/delete/{reservationid}")
	public String reviewDelete(@PathVariable("reservationid") Integer reservationid, Model theModel) {
		// 예약한 id를 가져와서 review에서 그 id를 토대로 삭제
		reviewService.deleteByReservationInfoId(reservationid);

		// 리뷰가 삭제됐기 때문에 별점에 변동이 생긴 그래서 별점 다시 계산
		ReservationInfo reserva = reservationInfoService.findById(reservationid);
		Integer reser_id = reserva.getRestaurant().getId();
		reservationInfoService.findCore(reser_id);

		return "redirect:/mypage/reservation";
	}

	// 태식 - 문의 조회
	@GetMapping("/inquiry")
	public String inquiry(Authentication auth, Model theModel,
			@PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
			@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(name = "target", required = false, defaultValue = "1") Integer target) {

		Member member = memberService.findByUserId(auth.getName());
		// OneToOnePageDTO inquiry =
		// oneToOneInquiryService.getOneToOneList(member.getId(),keyword,target,pageable);
		theModel.addAttribute("inquiry",
				oneToOneInquiryService.getOneToOneList(member.getId(), keyword, target, pageable));
		theModel.addAttribute("keyword", keyword);
		theModel.addAttribute("target", target);

		return "mypage/myInquiry";
	}

	// 태식 - 문의 상세보기
	@GetMapping("/inquiry/{id}")
	public String inquirydetail(@PathVariable("id") Integer inquiryId, Model theModel) {
		Map<String, OneToOneDTO> inquiry = oneToOneInquiryService.getOneToOnePageById(inquiryId);
		theModel.addAttribute("inquiry", inquiry);
		OneToOneInquiryComment com = oneToOneInquiryService.findOneToOneInquiryId(inquiryId);
		if (com == null) {
			theModel.addAttribute("comment", null);
		} else {
			theModel.addAttribute("comment", com);
		}

		return "mypage/myInquiry_detail";
	}

	@PostMapping("/inquiry/delete")
	public String inquirydelete(@RequestParam("oneToOneId") Integer inquiryId) {
		oneToOneInquiryService.deleteById(inquiryId);

		return "redirect:/mypage/inquiry";
	}
}