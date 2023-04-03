package com.reservation.foodTable.controller.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.foodTable.Service.AreaService;
import com.reservation.foodTable.Service.RestaurantService;
import com.reservation.foodTable.dto.AreaDTO;
import com.reservation.foodTable.dto.FilterFormDTO;
import com.reservation.foodTable.dto.RestaurantInfo;



@RestController
public class SearchRestController {
	@Autowired
	private AreaService areaRepo;

	@Autowired
	private RestaurantService restaurantRepo;

	@GetMapping("/request/area")
	public List<AreaDTO> checkDuplicateEmail(@RequestParam("areaValue") Integer id) {
		
		return areaRepo.findByName(id).stream().map(AreaDTO::new).toList();
	}

	@PostMapping("/search")
	public List<RestaurantInfo> search(@RequestBody FilterFormDTO searchForm) {
		List<RestaurantInfo> restaurantNamelist = restaurantRepo.searchRestaurantByKeyword(searchForm.getFilterId(),
				searchForm.getSortingId(), searchForm.getSearchQuery(), searchForm.getCategoryList(),
				searchForm.getPriceList(), searchForm.getAreaList(), searchForm.getGuList(), searchForm.getDongList());
		return restaurantNamelist;
	}
}
