package com.reservation.foodTable.controller.restaurant;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.reservation.foodTable.Service.MemberService;
import com.reservation.foodTable.Service.MenuService;
import com.reservation.foodTable.Service.RestaurantAvaTimeService;
import com.reservation.foodTable.Service.RestaurantService;
import com.reservation.foodTable.Service.ReviewService;
import com.reservation.foodTable.dto.AvailabilityData;
import com.reservation.foodTable.dto.MenuDTO;
import com.reservation.foodTable.dto.MenuListDTO;
import com.reservation.foodTable.dto.RestaurantInfo;
import com.reservation.foodTable.dto.ReviewDTO;
import com.reservation.foodTable.dto.ReviewListDTO;
import com.reservation.foodTable.entity.OwnerComment;
import com.reservation.foodTable.entity.Review;
import com.reservation.foodTable.exception.NotFoundAvailityTimeException;
import com.reservation.foodTable.exception.NotFoundRestaurantException;
import com.reservation.foodTable.exception.NotFoundReviewException;

@Controller
public class RestaurantController {
	

	private final RestaurantService restaurantService; 
	private final ReviewService reviewService;
	private final MenuService menuService;
	private final RestaurantAvaTimeService restaurantAvaTimeService;
	private final MemberService memberService;
	
	public RestaurantController(RestaurantAvaTimeService restaurantAvaTimeService,RestaurantService restaurantService,
			ReviewService reviewService,MenuService menuService, MemberService memberService) {
		this.restaurantAvaTimeService = restaurantAvaTimeService;
		this.restaurantService = restaurantService;
		this.reviewService = reviewService;
		this.menuService = menuService;
		this.memberService = memberService;
	}
	
	
	@GetMapping("/restaurant/{id}")
	public String home1(
			@PageableDefault(size = 5, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
			@PathVariable("id") int id, Model model) throws NotFoundRestaurantException {

		RestaurantInfo restaurantInfo = restaurantService.findRestaurantInfo(id);
		model.addAttribute("restaurantInfo", restaurantInfo);
		// 리뷰가 없는 경우에 대한 처리 추가해야됨 ( 프론트에서 Overview 데이가 null 이면 이라고 처리했음 )
		Page<ReviewDTO> reviewPage = reviewService.getSortedPagedReviews(id, pageable);

		// Overview 에 들어가는 최근 댓글 3개를 따로 요청하지 않고 가져온 데이터에서 3개를 가져오기때문에
		// 리스트가 비어 있는 경우를 따로 분기처리 할 필요가 없어서 빼고 넣음.
		List<ReviewDTO> reviewList = reviewPage.getContent();
		int size = reviewList.size();
		model.addAttribute("OverviewReview", reviewList.subList(0, Math.min(size, 3)));
		try {
			model.addAttribute("review", new ReviewListDTO(reviewPage));
		}catch(NotFoundReviewException e) {
			model.addAttribute("reviewIsNotExist", "등록된 리뷰가 없습니다.");
		}
		

		// 가져온 데이터에서 대표 메뉴라는 정보를 가진 애들만을 가지는 List를 stream의 filter 를 이용해서 생성
		List<MenuListDTO> menus = menuService.retrieveMenus(id);
		List<MenuDTO> representativeMenuList = menus.stream().flatMap(menuList -> menuList.getMenus().stream())
				.filter(MenuDTO::isRepresentative).collect(Collectors.toList());

		model.addAttribute("menuList", menus);
		model.addAttribute("representativeMenuList", representativeMenuList);

		//
		List<LocalDate> availDateList = restaurantAvaTimeService.availDay(id);
		if (availDateList.isEmpty()) {
			// 출력해줄 메세지를 가져오는 것도 괜찮아 보인다. 예약 가능한 날짜가 없습니다.
			model.addAttribute("dateListNullMessage", "예약 가능한 날짜가 존재하지 않습니다.");
			return "restaurant";
		}
		
		// 날짜를 가져오는 시점과
		for (; ; ) {
		    try {
		    	List<AvailabilityData>  a = restaurantAvaTimeService.queryAvailabilityData(id, availDateList.get(0).toString());
		    	System.out.println("here : "+a);
		        model.addAttribute("timeList",a);
		        break;
		    } catch (NotFoundAvailityTimeException e) {
		    	availDateList.remove(0);
		        if (availDateList.isEmpty()) {
		            model.addAttribute("dateListNullMessage", "예약 가능한 날짜가 존재하지 않습니다.");
		            break;
		        }
		    }
		}	
		
		model.addAttribute("calendar", dates(availDateList));
		model.addAttribute("recentDay", availDateList.get(0));

		return "restaurant";
	}

	
	List<List<AvailDate>> dates(List<LocalDate> dateList){
		
		// 1. 오늘 날짜가 일요일이 아니면 123456 만큼 전날만들고 avail=false인 애들로 만들어서 List에 더해준다.
		// 
		
		LocalDate start = dateList.get(0);
		LocalDate end = dateList.get(dateList.size()-1);
		
		int startDays = start.getDayOfWeek().getValue();
		LocalDate forStart;
		
		if( startDays <7 ) {
			forStart = start.minusDays(startDays);
		}else {
			forStart = start;
		}
		
		int endDays = end.getDayOfWeek().getValue();
		
		LocalDate forEnd;
		if( endDays!=6) {
			forEnd = end.plusDays(7-(endDays%7));
		}else {
			forEnd = end.plusDays(1);
		}
		
		List<List<AvailDate>> availDates = new ArrayList<>();
		List<AvailDate> subAvailDates = new ArrayList<>();
		int i = 0;
		int j =0;
		int maxSize = dateList.size()-1;
		for(LocalDate date = forStart; date.isBefore(forEnd);date=date.plusDays(1)) {
			
			if((i++)%7==0) {
				
				if(i!=1) availDates.add(subAvailDates);
				
				subAvailDates = new ArrayList<>();
			}
			
			if(j>maxSize) {
				subAvailDates.add(new AvailDate(date,false));
				continue;
			}
			
			if(dateList.get(j).equals(date)) {
				
				j++;
				subAvailDates.add(new AvailDate(date,true));
			}else {
				subAvailDates.add(new AvailDate(date,false));
			}
			
		}
		availDates.add(subAvailDates);
		return availDates;
	}
	

	class AvailDate{
		LocalDate date;
		boolean avail;
		public LocalDate getDate() {
			return date;
		}
		public void setDate(LocalDate date) {
			this.date = date;
		}
		public boolean isAvail() {
			return avail;
		}
		public void setAvail(boolean avail) {
			this.avail = avail;
		}
		public AvailDate(LocalDate date, boolean avail) {

			this.date = date;
			this.avail = avail;
		}
		@Override
		public String toString() {
			return "AvailDate [date=" + date + ", avail=" + avail + "]";
		}
		public String date() {
			return date.getMonthValue()+"/"+date.getDayOfMonth();
		}
		
	}
	
	   /////////////////////////////////////하지원////////////////////////////////////////////////////////////////////
	@GetMapping("/restaurant-page/reviewList")
	public String restaurantReviewList(@RequestParam(name = "page", defaultValue = "1") int pageNum,
			@RequestParam(name = "select", required = false) String myParamValue, Model model, Principal p) {

		String loginOwnerId = p.getName();
		int restaurantId = memberService.myRestaurantId(loginOwnerId);
		System.out.println("loginOwnerId" + loginOwnerId);

		System.out.println("myParamValue::::::"+ myParamValue);

		if (myParamValue != null && myParamValue.equals("getAllReview")) {
			Page<Review> reviewList = reviewService.findAllReviewByRestaurantId(restaurantId, pageNum);
			model.addAttribute("getSize", " 전체리뷰 개수 : " + reviewList.getTotalElements());
			model.addAttribute("reviewList", reviewList);
		}if(myParamValue != null && myParamValue.equals("getReviewWihtOwnerComment")) {
			Page<Review> reviewList = reviewService.findReviewByRestaurantIdHavingOwnerContent(restaurantId, pageNum);
			model.addAttribute("getSize", " 나의리뷰 개수 : " + reviewList.getTotalElements());
			model.addAttribute("reviewList", reviewList);
		}
		else {
			Page<Review> reviewList = reviewService.findAllReviewByRestaurantId(restaurantId, pageNum);
			model.addAttribute("getSize", " 전체리뷰 개수 : " + reviewList.getTotalElements());
			model.addAttribute("reviewList", reviewList);
		}

		return "/restaurantPage/restaurantReview";
	}
	// 리뷰 삭제
	@GetMapping("/restaurant-page/delete/{ownerReviewId}")
	public String deleteOwnerReview(@PathVariable int ownerReviewId, Principal p) {
		String loginOwnerId = p.getName();
		int restaurantId = memberService.myRestaurantId(loginOwnerId);
		reviewService.deleteByOwnerCommentId(ownerReviewId);
		return "redirect:/restaurant-page/reviewList?page=1";
	}

	// 리뷰 등록
	@GetMapping("/restaurant-page/add/{ownerReviewId}")
	public String addOwnerReview(@PathVariable("ownerReviewId") int ownerReviewId, OwnerComment ownerComment) {
		Review review = reviewService.getReviewById(ownerReviewId);
		if (review.getOwnerComment() != null) {
			reviewService.deleteByOwnerCommentId(review.getOwnerComment().getId());
			OwnerComment comment = new OwnerComment();
			comment.setContent(ownerComment.getContent());
			comment.setReview(ownerComment.getReview());
			reviewService.save(comment);
			return "redirect:/restaurant-page/reviewList?page=1";
		} else {
			OwnerComment comment = new OwnerComment();
			comment.setContent(ownerComment.getContent());
			comment.setReview(ownerComment.getReview());
			reviewService.save(comment);
			return "redirect:/restaurant-page/reviewList?page=1";
		}
	}


	   
	   /////////////////////////////////////////////////////////////////////////////////////////////////////////
}
