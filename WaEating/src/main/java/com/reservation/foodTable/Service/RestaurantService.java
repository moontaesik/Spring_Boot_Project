package com.reservation.foodTable.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.foodTable.dto.RestaurantInfo;
import com.reservation.foodTable.dto.RestaurantInfoPage;
import com.reservation.foodTable.entity.MenuCategory;
import com.reservation.foodTable.entity.Restaurant;
import com.reservation.foodTable.entity.RestaurantDefaultTime;
import com.reservation.foodTable.repository.MenuCategoryRepository;
import com.reservation.foodTable.repository.RestaurantDefaultTimeRepository;
import com.reservation.foodTable.repository.RestaurantRepository;
import com.reservation.foodTable.repository.RestaurantRepository.HighScoreRestaurant;

@Service
@Transactional
public class RestaurantService {
	public static final int RESTAURANTS_PER_PAGE=5;

	private final RestaurantRepository restaurantRepository;
	private final RestaurantDefaultTimeRepository restaurantDefaultTimeRepository;
	private final MenuCategoryRepository menuCategoryRepository;

	public RestaurantService(RestaurantRepository restaurantRepository,RestaurantDefaultTimeRepository restaurantDefaultTimeRepository,
			MenuCategoryRepository menuCategoryRepository) {
		this.restaurantDefaultTimeRepository = restaurantDefaultTimeRepository;
		this.restaurantRepository = restaurantRepository;
		this.menuCategoryRepository = menuCategoryRepository;
	}

	/*
	 * 누구꺼지?
	 */
	public RestaurantInfo findRestaurantInfo(int restaurantId) {
		Optional<Restaurant> re = restaurantRepository.findOneById(restaurantId);
		System.out.println(re);
		return new RestaurantInfo(re.get());
	}

	//  ================================ < 하지원 > ================================
	
	
	private static Pageable pageable = PageRequest.of(0, 10);

	// ✔ 비회원일 경우 전체지역 전체 카테고리의 식당들에서 Total 별점이 높은 식당데이터 받아오는 함수
	public List<HighScoreRestaurant> findTopScoreRestaurantForGuest() {

		return restaurantRepository.findTopScoreRestaurant(pageable);
	}

	// ✔ 회원일 경우 관심 지역 관심 카테고리의 식당들에서 Total 별점이 높은 식당 데이터 받아오는 함수
	public List<HighScoreRestaurant> findTopScoreRestaurantForMember(Integer id) {

		List<Integer> userArea = restaurantRepository.getUserInterestArea(id);
		List<Integer> userCat = restaurantRepository.getUserInterestCategories(id);

		return restaurantRepository.findTopScoreRestaurant(userArea, userCat, pageable);

	}

	// ✔ 비회원일 경우 전체지역 전체 카테고리의 식당들에서 리뷰가 많은은 식당데이터 받아오는 함수
	public List<HighScoreRestaurant> findTopReviewCountRestaurantForGuest() {

		LocalDate currentTime = LocalDate.now();
		LocalDate lastMonthTime = currentTime.minusMonths(1);
		return restaurantRepository.findTopReviewCountRestaurant(lastMonthTime, pageable);

	}

	// ✔ 회원일 경우 관심 지역 관심 카테고리의 식당들에서 리뷰가 많은은 식당데이터 받아오는 함수
	public List<HighScoreRestaurant> findTopReviewCountRestaurantForMember(Integer id) {

		LocalDate currentTime = LocalDate.now();
		LocalDate lastMonthTime = currentTime.minusMonths(1);

		List<Integer> userArea = restaurantRepository.getUserInterestArea(id);
		List<Integer> userCat = restaurantRepository.getUserInterestCategories(id);

		return restaurantRepository.findTopReviewCountRestaurant(lastMonthTime, userArea, userCat, pageable);

	}

	// ✔ 비회원일 경우 전체지역 전체 카테고리의 식당들에서 예약이 많은은 식당데이터 받아오는 함수
	public List<HighScoreRestaurant> findTopReservationCountRestaurantForGuest() {
		LocalDate currentTime = LocalDate.now();
		LocalDate lastMonthTime = currentTime.minusMonths(1);

		return restaurantRepository.findTopReservationCountRestaurant(lastMonthTime, pageable);
	}

	// ✔ 회원일 경우 관심 지역 관심 카테고리의 식당들에서 예약이 많은은 식당데이터 받아오는 함수
	public List<HighScoreRestaurant> findTopReservationCountRestaurantForGuest(Integer id) {

		LocalDate currentTime = LocalDate.now();
		LocalDate lastMonthTime = currentTime.minusMonths(1);

		List<Integer> userArea = restaurantRepository.getUserInterestArea(id);
		List<Integer> userCat = restaurantRepository.getUserInterestCategories(id);

		return restaurantRepository.findTopReservationCountRestaurant(lastMonthTime, userArea, userCat, pageable);

	}

	
	//  ================================ < 오세종 > ================================
	
	public List<RestaurantInfo> searchRestaurantByKeyword(int filterId, int sortingId, String searchQuery,
			List<Integer> categoryList, List<String> priceList, List<Integer> areaList, List<Integer> guList,
			List<Integer> dongList) {
		return restaurantRepository.searchRestaurantByKeyword(filterId, sortingId, searchQuery, categoryList, priceList,
				areaList, guList, dongList);
	}

//	public List<RestaurantInfo> searchRestaurantByKeyword(String searchQuer) {
//		return restaurantRepository.searchRestaurantByKeyword(searchQuer);
//	}

	public Page<RestaurantInfo> searchRestaurantByKeyword(String searchQuer, Pageable pageable) {
		return restaurantRepository.searchRestaurantByKeyword(searchQuer,pageable);
	}

	public Restaurant save(Restaurant restaurant,String[] times) {
		Restaurant addRestaurant = restaurantRepository.save(restaurant);
		
		for(String time : times) {
		RestaurantDefaultTime addTime = new RestaurantDefaultTime(time,addRestaurant);
		restaurantDefaultTimeRepository.save(addTime);
		}
		
		return addRestaurant;
	}
	
	public Restaurant update(Restaurant restaurant,String[] times) {
		// 왜 스코어 초기화해??
		System.err.println("times : "+times);
		Restaurant update = restaurantRepository.findById(restaurant.getId()).get();
		update.setBusinessHour(restaurant.getBusinessHour());
		if(restaurant.getImg()!=null) {
			update.setImg(restaurant.getImg());
		}
		update.setMaxTable(restaurant.getMaxTable());
		update.setName(restaurant.getName());
		update.setPhone(restaurant.getPhone());
		update.setPriceRange(restaurant.getPriceRange());

		
		// 여기서 해당 레스토랑의 time 전체 리스트를 가져온다 기존 A 새로운거 B 
		// A - B 는 disabled 
		// B - A 는 INSERT 
		// A 교집합 B 는 able
		
		List<LocalTime> restaurantTimeList = restaurantDefaultTimeRepository.findByRestaurantId(restaurant.getId());
		
		List<LocalTime> selectedTimeList = Arrays.asList(times).stream().map(LocalTime::parse).toList();
		
		
	     
	    List<LocalTime> disabledTimeList = new ArrayList<>(restaurantTimeList);
	    disabledTimeList.removeAll(selectedTimeList);

	    List<LocalTime> insertTimeList = new ArrayList<>(selectedTimeList);
	    insertTimeList.removeAll(restaurantTimeList);

	    List<LocalTime> abledTimeList = new ArrayList<>(restaurantTimeList);
	    abledTimeList.retainAll(selectedTimeList);

	    restaurantDefaultTimeRepository.disabledInTimeAndRestaurantId(disabledTimeList,restaurant.getId());
	    
	    restaurantDefaultTimeRepository.abledInTimeAndRestaurantId(abledTimeList,restaurant.getId());
	    
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); 
        
		for(LocalTime time : insertTimeList) {
			
			RestaurantDefaultTime addTime = new RestaurantDefaultTime(time.format(formatter),update);
			restaurantDefaultTimeRepository.save(addTime);
		}
		
		return update;
	}
	public List<Restaurant> findAll() {	
		return restaurantRepository.findAll();
	}

	public Restaurant getRestaurant(Integer id) {
		return restaurantRepository.findById(id).get();
	}

	public Restaurant save(Restaurant restaurant) {
		Restaurant update = restaurantRepository.findById(restaurant.getId()).get();
		update.setBusinessHour(restaurant.getBusinessHour());
		update.setImg(restaurant.getImg());
		update.setMaxTable(restaurant.getMaxTable());
		update.setName(restaurant.getName());
		update.setPhone(restaurant.getPhone());
		update.setPriceRange(restaurant.getPriceRange());
		update.setResIntro(restaurant.getResIntro());
		
		return restaurantRepository.save(update);
		
	}

	public String getRestaurantName(Integer id) {
		
		return restaurantRepository.findById(id).get().getName();
	}

	public Page<Restaurant> listByPage(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, RESTAURANTS_PER_PAGE);

		return restaurantRepository.findAll(pageable);
	}

	public Restaurant update(Restaurant restaurant) {
		
		return restaurantRepository.save(restaurant);
	}

	public List<MenuCategory> myMenuCategory(int id) {
		// TODO Auto-generated method stub
		return menuCategoryRepository.findByRestaurantIdOrderByOrderNumberAsc(id);
	}

	public void saveMenuCategory(String name, Integer orderNumber, Integer restaurantId) {
		// TODO Auto-generated method stubString name,Integer restaurantId, int orderNumber
		MenuCategory menuCategory = new MenuCategory(name,restaurantId,orderNumber);
		
		menuCategoryRepository.save(menuCategory);
		
	}

	public void categoryOrderUpdate(List<Integer> menuIds) {
		// TODO Auto-generated method stub
		for(int i =1; i<=menuIds.size();i++) {
			
			menuCategoryRepository.updateOrder(i,menuIds.get(i-1));
		}
		
	}

	public RestaurantInfoPage listByPage(String keyword, Integer target, Pageable pageable) {
		if(keyword.equals("")) {
	         return new RestaurantInfoPage(restaurantRepository.findAll(pageable));
	      }
	      switch(target) {
	      case 1: // 점주이름으로 검색
	         return new RestaurantInfoPage(restaurantRepository.findByMemberName(keyword,pageable));
	      case 2: // 연락처 으로 검색
	         return new RestaurantInfoPage(restaurantRepository.findByMemberPhone(keyword,pageable));
	      }    
	      return null; // 여기 에러
	}


}
