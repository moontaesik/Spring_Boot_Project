package com.reservation.foodTable.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.reservation.foodTable.dto.RestaurantInfo;

public interface RestaurantRepositoryCustom {

	public Page<RestaurantInfo> searchRestaurantByKeyword(String searchQuer,Pageable pageable);
	public List<RestaurantInfo> searchRestaurantByKeyword(int filterId,int sortingId,String searchQuery, List<Integer> categoryList,
			List<String> priceList, List<Integer> areaList,List<Integer> guList,List<Integer> dongList);
}
