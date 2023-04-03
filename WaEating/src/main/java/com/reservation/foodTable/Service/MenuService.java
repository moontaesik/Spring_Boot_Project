package com.reservation.foodTable.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.foodTable.dto.MenuListDTO;
import com.reservation.foodTable.entity.Menu;
import com.reservation.foodTable.entity.MenuCategory;
import com.reservation.foodTable.entity.RestaurantCategory;
import com.reservation.foodTable.repository.CategoryRepository;
import com.reservation.foodTable.repository.MenuCategoryRepository;
import com.reservation.foodTable.repository.MenuRepository;
import com.reservation.foodTable.repository.ReservationInfoRepository;
import com.reservation.foodTable.repository.RestaurantCategoryRepository;
import com.reservation.foodTable.repository.RestaurantRepository;


@Service
@Transactional(readOnly=true)
public class MenuService {
	
	private final MenuRepository menuRepository;
	private final ReservationInfoRepository reservationInfoRepository;
	private final MenuCategoryRepository menuCategoryReposity;
	private final RestaurantRepository restaurantRepository;
	private final CategoryRepository categoryRepository;
	private final RestaurantCategoryRepository restaurantCategoryRepository;
	
	public MenuService(RestaurantCategoryRepository restaurantCategoryRepository,CategoryRepository categoryRepository,MenuRepository menuRepository,ReservationInfoRepository reservationInfoRepository,MenuCategoryRepository menuCategoryReposity,RestaurantRepository restaurantRepository) {
		this.menuRepository = menuRepository;
		this.reservationInfoRepository = reservationInfoRepository;
		this.menuCategoryReposity = menuCategoryReposity;
		this.restaurantRepository = restaurantRepository;
		this.categoryRepository =categoryRepository;
		this.restaurantCategoryRepository = restaurantCategoryRepository;
	}
	
	/*
	 * 3월 10일
	 * 한별
	 * 1. public List<Menu> retrieveMenus(int restaurantId)
	 * */
	public List<MenuListDTO> retrieveMenus(int restaurantId){
		
		return MenuListDTO.convertMenuListDto(menuRepository.findByRestaurant_Id(restaurantId));
	}
	
	public List<MenuListDTO> retrieveMenusByReservationId(int reservationId){
		
		int restaurantId = reservationInfoRepository.findRestaurantId(reservationId);
		return MenuListDTO.convertMenuListDto(menuRepository.findByRestaurant_Id(restaurantId));
	}
	
	public List<Menu> getMenus(Integer id) {
		
		return menuRepository.findByRestaurant_Id(id);
			
	}

	public Menu getMenu(Integer id) {
		return menuRepository.findById(id).get();
	}

	@Transactional
	public Menu save(Menu menu, Integer id) {
		
		menu.getMenuCategory().setRestaurant(restaurantRepository.findById(id).get());
		MenuCategory menuCategory =  menuCategoryReposity.save(menu.getMenuCategory());
		menu.setRestaurant(restaurantRepository.findById(id).get());
		RestaurantCategory restaurantCategory = restaurantCategoryRepository.finbyReataurant(menu.getRestaurant());
		menu.setMenuCategory(menuCategory);
		menu.setCategory(categoryRepository.findById(restaurantCategory.getCategory().getId()).get());
		Menu saveMenu = menuRepository.save(menu);
		
		return saveMenu;
	}

	@Transactional
	public void deleteMenu(Integer id) {
		menuCategoryReposity.delete(menuRepository.findById(id).get().getMenuCategory());
		menuRepository.delete(menuRepository.findById(id).get());
	}
	@Transactional
	public void menuRepresentRegister(Integer restaurantId, List<Integer> menuIds) {
		// TODO Auto-generated method stub
		menuRepository.representFalse(restaurantId);
		menuRepository.representRegister(menuIds);
	}
	
}
