//package com.reservation.foodTable.QueryDslTest;
//
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.reservation.foodTable.dto.RestaurantInfo;
//import com.reservation.foodTable.entity.QMenu;
//import com.reservation.foodTable.entity.QRestaurant;
//import com.reservation.foodTable.entity.Restaurant;
//
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@Import(JpaQueryConfig.class)
//// @Transactional
//public class QueryDslTest {
//	
//
//	
//	@Autowired
//	JPAQueryFactory query;
//
//	@Test
//	public void 기본쿼리() {
//		//가게 이름으로 검색하는 쿼리문
//		List<RestaurantInfo> allResults = new ArrayList<>();
//		
//		List<Restaurant> results = query
//				.selectFrom(QRestaurant.restaurant)
//				.where(QRestaurant.restaurant.name.eq("대륙"))
//				.fetch();
//		
//		System.out.println(results.toString());
//	}
//	
//	@Test
//	public void 기본쿼리2() {
//		List<RestaurantInfo> allResults = new ArrayList<>();
//		
//		List<Restaurant> results = query
//				.selectFrom(QRestaurant.restaurant)
//				.leftJoin(QMenu.menu)
//				.on(QMenu.menu.restaurant.id.eq(QRestaurant.restaurant.id))
//				.where(QMenu.menu.name.like("%"+"김치"+"%"))
//				.fetch();
//		System.out.println(results.toString());
//			
//	}
//}
