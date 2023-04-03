package com.reservation.foodTable.repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reservation.foodTable.dto.AvailabilityData;
import com.reservation.foodTable.entity.RestaurantAvaTime;

public interface RestaurantAvaTimeRepository extends JpaRepository<RestaurantAvaTime, Integer> {
	

	@Query("SELECT new com.reservation.foodTable.dto.AvailabilityData(r.id,rd.time,r.tableInventory) FROM RestaurantAvaTime r "
			+ "join r.restaurantDefaultTime rd WHERE rd.restaurant.id = :id AND r.date =:date ORDER BY rd.time")
	public List<AvailabilityData> findAvailabilityData(@Param("id") int id,@Param("date") LocalDate date);
	
	
	/*
	 * findAvailabilityData의 오늘버전으로 시간까지 비교해서 데이터를 가져오도록 설정
	 * */
	@Query("SELECT new com.reservation.foodTable.dto.AvailabilityData(r.id,rd.time,r.tableInventory) FROM RestaurantAvaTime r "
			+ "join r.restaurantDefaultTime rd WHERE rd.restaurant.id = :id AND r.date =:date AND rd.time >= :time ORDER BY rd.time")
	public List<AvailabilityData> findAvailabilityTodayData(@Param("id") int id,@Param("date") LocalDate date,@Param("time")LocalTime time);
	
	
	// 검색 -> 검색필터 정렬필터 / ( ( 지역명 ) ( 지역명 ) ( 지역명 ) + ( 가게명 ) +  ( 메뉴명 )  )  
	// 드랍다운으로 넣는 방식 ( 전체 지역 가게 메뉴 )
	
	// 기존 -> 신규 : 오늘 + 2시간 이상의 데이터만 가지고 SUM 을진행한 결과를 보내도록 진행
	@Query("SELECT r.date "
			+ "FROM RestaurantAvaTime r Join r.restaurantDefaultTime rd "
			+ "WHERE TIMESTAMP(CONCAT(r.date, ' ', rd.time)) >= :nowDateTime"
			+ " AND rd.restaurant.id =:id Group By r.date Having Sum(r.tableInventory) >0 order by r.date")
	public List<LocalDate> findAvailableDate(@Param("id")int id,@Param("nowDateTime")Timestamp nowDateTime);

	

}
