package com.reservation.foodTable.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.reservation.foodTable.Service.CustomerSupportService;
import com.reservation.foodTable.Service.MemberService;
import com.reservation.foodTable.Service.RestaurantService;
import com.reservation.foodTable.entity.Banner;
import com.reservation.foodTable.repository.RestaurantRepository.HighScoreRestaurant;

// 홈화면 관련 컨트롤러


@Controller
public class HomeController {

	private final RestaurantService restaurantService;
	private final MemberService memberService;
	private final CustomerSupportService customerSupportService;
	public HomeController(RestaurantService restaurantService,MemberService memberService, CustomerSupportService customerSupportService) {
		
		this.restaurantService = restaurantService;
		this.memberService = memberService;
		this.customerSupportService = customerSupportService;
	}


	@GetMapping("")
	public String mainPage(Model model,Principal p) {
			
		List<HighScoreRestaurant> topScoreRestaurantList;
		List<HighScoreRestaurant> topReviewCountRestaurantList;
		List<HighScoreRestaurant> topReservationCountRestaurantList;
		String title1 = null;
		String title2 = null;
		String title3 = null;
		List<String> starRatings = null;
		
		if (p==null) {
			
			topScoreRestaurantList = restaurantService.findTopScoreRestaurantForGuest();
			topReviewCountRestaurantList = restaurantService.findTopReviewCountRestaurantForGuest();
			topReservationCountRestaurantList = restaurantService.findTopReservationCountRestaurantForGuest();
		
			title1 = "평점이 높은 인기 식당 Top10";
			title2 = "리뷰가 많은 인기 식당 Top10";
			title3 = "예약이 많은 인기 식당 Top10";
			
		} else {
			
			Integer id = memberService.findByUserId(p.getName()).getId();
			topScoreRestaurantList = restaurantService.findTopScoreRestaurantForMember(id);
			topReviewCountRestaurantList = restaurantService.findTopReviewCountRestaurantForMember(id);
			topReservationCountRestaurantList = restaurantService.findTopReservationCountRestaurantForGuest(id);
			
			title1 = "내 관심 지역/카테고리 가게 별점순  Top10";
			title2 = "내 관심 지역/카테고리 가게 리뷰 많은순  Top10";
			title3 = "내 관심 지역/카테고리 가게 예약 많은순  Top10";
		}
		      
	      Banner banner = new Banner();
	      model.addAttribute("banner", banner);
	      List<Banner> bannerList = customerSupportService.findAllBanner();
	      model.addAttribute("bannerList", bannerList);
	      
		model.addAttribute("highScoreResTitle", title1);
		model.addAttribute("orderByReviewInMonthTitle", title2);
		model.addAttribute("orderByResInMonthTitle", title3);
		
		model.addAttribute("highScoreRes", topScoreRestaurantList);
		model.addAttribute("orderByReviewInMonth", topReviewCountRestaurantList);
		model.addAttribute("orderByResInMonth", topReservationCountRestaurantList);
       
		model.addAttribute("starRatings", starRatings);
		return "main";
	}
	
	   @GetMapping("/swiper")
	   public String banner(Model model) {
	      Banner banner = new Banner();
	      model.addAttribute("banner", banner);
	      List<Banner> bannerList = customerSupportService.findAllBanner();
	      model.addAttribute("bannerList", bannerList);
	      return "/fragments/mainSwiper";
	   }
	
}
