package com.reservation.foodTable.controller.restaurant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.foodTable.Service.RestaurantAvaTimeService;
import com.reservation.foodTable.Service.ReviewService;
import com.reservation.foodTable.dto.AvailabilityData;
import com.reservation.foodTable.dto.ReviewDTO;
import com.reservation.foodTable.dto.ReviewListDTO;
import com.reservation.foodTable.exception.NotFoundAvailityTimeException;
import com.reservation.foodTable.exception.NotFoundReviewException;

@RestController
public class RestaurantRestController {
	
	private final ReviewService reviewService;
	private final RestaurantAvaTimeService avaTimeService;
	
	
	public RestaurantRestController(ReviewService reviewService,RestaurantAvaTimeService avaTimeService) {

		this.reviewService = reviewService;
		this.avaTimeService = avaTimeService;
	}


	// 레스토랑 메인 페이지에서 리뷰의 비동기 처리를 위한 컨트롤러
	
	@GetMapping("/restaurant/review/{restaurantId}")
	public ReviewListDTO reviews(@PathVariable("restaurantId") int id,@PageableDefault(size=5,sort = "id", direction = Sort.Direction.DESC)Pageable pageable) throws NotFoundReviewException {
		
		//id 로 레스토랑을 구분하고 pageable로 어떤 페이지인지 구별하는 함수
		Page<ReviewDTO> reviewPage =reviewService.getSortedPagedReviews(id,pageable);
		
		
		// 요청한 페이지의 데이터가 없는 경우
		return new ReviewListDTO(reviewPage);
		
	}
	
	@GetMapping("/restaurant/avaTime/{restaurantId}")
	public List<AvailabilityData> availableTime(@PathVariable("restaurantId")int restaurantId,@RequestParam("date")String date) throws NotFoundAvailityTimeException{
		
	
		List<AvailabilityData> availabilityData = new ArrayList<>();
		availabilityData= avaTimeService.queryAvailabilityData(restaurantId, date);
		return availabilityData;
	}
}
