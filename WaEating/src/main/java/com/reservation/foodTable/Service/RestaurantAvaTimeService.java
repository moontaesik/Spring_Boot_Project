package com.reservation.foodTable.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.foodTable.dto.AvailabilityData;
import com.reservation.foodTable.exception.NotFoundAvailityTimeException;
import com.reservation.foodTable.repository.RestaurantAvaTimeRepository;

@Service
@Transactional(readOnly=true)
public class RestaurantAvaTimeService {

	private final RestaurantAvaTimeRepository restaurantAvaTimeRepository; 
	
	
	public RestaurantAvaTimeService(RestaurantAvaTimeRepository restaurantAvaTimeRepository) {
		super();
		this.restaurantAvaTimeRepository = restaurantAvaTimeRepository;
	}
	
	/*
	 * 3월 10일
	 * 한별
	 * 1. public List<AvailabilityData> queryAvailabilityData(int id,String date)
	 * 2. public List<LocalDate> availDay(int id)
	 * */

	public List<AvailabilityData> queryAvailabilityData(int id,String date) throws NotFoundAvailityTimeException{

		LocalDate now = LocalDate.now();
		LocalDate inputDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
		
		List<AvailabilityData> availTimeList;
		
		// 날짜가 오늘인 경우에는 예약 가능 한 시간에 지금 시간 + 2시간으로 조회하기 위해 분기 설정
		if(now.isEqual(inputDate)) {
			LocalTime reservationAvailTime = LocalTime.now().plusHours(2);
			availTimeList = restaurantAvaTimeRepository.findAvailabilityTodayData(id, inputDate,reservationAvailTime);
			System.out.println("heres : "+availTimeList);
			// 가져온 데이터가 
			if(availTimeList.isEmpty()||availTimeList.stream().allMatch(AvailabilityData::isTableUnavailable)) {
				throw new NotFoundAvailityTimeException(inputDate.toString());
			}
			
			return availTimeList;
		}
		
		availTimeList = restaurantAvaTimeRepository.findAvailabilityData(id, inputDate);
		
		if(availTimeList.isEmpty()||availTimeList.stream().allMatch(AvailabilityData::isTableUnavailable)) {
			throw new NotFoundAvailityTimeException(inputDate.toString());
		}
		return availTimeList;
		
	}
	
	
	// 예약 가능한 시간인 2시간 까지의 모든 데이터를 가져올 수 있도록 한다.
	public List<LocalDate> availDay(int id){
		
		Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().plusHours(2));
		return restaurantAvaTimeRepository.findAvailableDate(id,timestamp);
	}
	
	

}
