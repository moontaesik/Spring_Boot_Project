//package com.reservation.foodTable.basic;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.HashSet;
//import java.util.Set;
//
//import org.junit.jupiter.api.Test;
//
//public class BasicTest1 {
//	
//	
//	@Test
//	void StringFormatTest(){
//		
//		int[] prices = new int[] {1000,10000,100000,1000000,10000000,200000000,100,10,1};
//	
//		for(int price :prices) {
//		System.out.printazxxln(String.format("price : "+price+" , 변경된 price : %,d원",price));
//		}
//	}
//	
//	@Test
//	void dateCompare() {
//		
//		LocalTime timeOfDay = LocalTime.of(12, 0);
//		LocalTime timeOfDay2 = LocalTime.of(12, 0);
//		System.out.println("isAfter : "+timeOfDay.isAfter(timeOfDay2));
//		System.out.println("isBefore : "+timeOfDay.isBefore(timeOfDay2));
//	}
//	@Test
//	void date() {
//		for(int no=5; no<72;no++) {
//		System.out.println("INSERT INTO `foodtable`.`review` (`content`, `atmosphere_score`, `price_score`, `service_score`, `taste_score`, `total_score`, `reservation_info_id`) VALUES ('Good!', '3', '3', '3', '3', '3', '"+no+"');");
//	}}
//	
//}