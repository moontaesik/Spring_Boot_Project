package com.reservation.foodTable.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import com.reservation.foodTable.entity.Menu;

public class MenuListDTO {
	
	private String categoryName;
	private List<MenuDTO> menus = new ArrayList<>();
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<MenuDTO> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuDTO> menus) {
		this.menus = menus;
	}

	private void addMenu(Menu menu) {
		menus.add(new MenuDTO(menu));
	}
	
	@Override
	public String toString() {
		return "MenuListDto [categoryName=" + categoryName + ", menus=" + menus + "]";
	}

	public static List<MenuListDTO> convertMenuListDto(List<Menu> menuList){
		
		List<MenuListDTO> data= new ArrayList<>();
		
		int size = menuList.get(0).getMenuCategory().getOrderNumber();
		
		for(int i = 0; i < size ; i++) {
			data.add(new MenuListDTO());
		}
		
		menuList.stream().forEach(menu->{
			MenuListDTO menuListDto = data.get(menu.getMenuCategory().getOrderNumber()-1);
			if(menuListDto.categoryName==null) {
				menuListDto.categoryName = menu.getMenuCategory().getName();
			}
			menuListDto.addMenu(menu);
		});
		
		return data;
	}
	

}
