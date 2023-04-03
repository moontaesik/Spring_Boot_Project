package com.reservation.foodTable.controller.search;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reservation.foodTable.Service.AreaService;
import com.reservation.foodTable.Service.CategoryService;
import com.reservation.foodTable.Service.RestaurantService;
import com.reservation.foodTable.dto.RestaurantInfo;
import com.reservation.foodTable.dto.ReviewDTO;
import com.reservation.foodTable.entity.Area;
import com.reservation.foodTable.entity.Category;
import com.reservation.foodTable.enumClass.PriceRange;

//================================ < 오세종 > ================================

@Controller
public class SearchController {


	private final RestaurantService restaurantService;

	private final AreaService areaService;
	private final CategoryService categoryService;

	public SearchController(RestaurantService restaurantService, AreaService areaService,
			CategoryService categoryService) {
		this.restaurantService = restaurantService;
		this.areaService = areaService;
		this.categoryService = categoryService;
	}


	/*
	 * 첫 화면에서 검색 했을 때 검색 결과를 가져오는 컨트롤러
	 * */
	@GetMapping("/search")
	public String getRestaurantList(@PageableDefault(size=5,page=0)Pageable pageable, @RequestParam("searchQuery") String searchQuer, Model model) {

		Page<RestaurantInfo> restaurantPage =restaurantService.searchRestaurantByKeyword(searchQuer,pageable);
//		List<RestaurantInfo> restaurantNamelist = restaurantService.searchRestaurantByKeyword(searchQuer);
		List<Category> categoryNameList = categoryService.getCategory();
		List<PriceRange> priceRangeList = Arrays.asList(PriceRange.values());
		List<Area> areaList = areaService.getAreaParent();

		model.addAttribute("restaurantList", restaurantPage);
		model.addAttribute("categoryList", categoryNameList);
		model.addAttribute("priceRangeList", priceRangeList);
		model.addAttribute("areaList", areaList);
		model.addAttribute("keyword",searchQuer);

		return "restaurant_search";
	}
}
