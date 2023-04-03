//package com.reservation.foodTable.search;
//
//import java.time.LocalDate;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Commit;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.reservation.foodTable.entity.RestaurantAvaTime;
//import com.reservation.foodTable.entity.RestaurantDefaultTime;
//import com.reservation.foodTable.repository.MenuRepository;
//import com.reservation.foodTable.repository.RestaurantAvaTimeRepository;
//
//
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@Transactional
//public class FoodTableSearchTest {
//
//	@Autowired
//	MenuRepository repo;
//	@Autowired
//	RestaurantAvaTimeRepository r;
//	@Test
//	public void MenuGetTest() {
//		/*
//		 * String keyword = "김치"; List<Menu> listMenu = repo.findByMenu(keyword);
//		 * 
//		 * listMenu.forEach(menu -> System.out.println(menu.toString()));
//		 * listMenu.forEach(restaurant ->
//		 * System.out.println(restaurant.getRestaurant().getName()));
//		 */
//	}
//	
//	@Test
//	@Commit
//	@Transactional
//	void insertQueryCreate() {
//		
//		LocalDate date = LocalDate.now().minusDays(1);
//
//		
//
//		
//		for(int i=0; i<14;i++) {
//
//			date = date.plusDays(1);
//			saveData(date);
//
//		}
//	}
//	
//	private void saveData(LocalDate date) {
//		
//		int[] remainTable = {0,5,10,15,20,25,30,3,7,13,18,21,24,28};
//		int length = remainTable.length;
//		
//		for(int j=1; j<10;j++) {
//			
//			int randomTableNum = remainTable[(int)(Math.random()*length)];
//			RestaurantAvaTime data = new RestaurantAvaTime(date,new RestaurantDefaultTime(j),randomTableNum);
//			r.save(data);
//		}
//	}
//	
//	@Test
//	@Commit
//	@Transactional
//	void insertQueryCreate2() {
//		
//		LocalDate date = LocalDate.of(2023,3,17);
//		saveData(date);
//
//	}
//	
//	
//}
