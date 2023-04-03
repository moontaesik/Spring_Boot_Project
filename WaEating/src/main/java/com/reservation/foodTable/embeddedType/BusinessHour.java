package com.reservation.foodTable.embeddedType;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;



@Embeddable
public class BusinessHour {
	
	@Column(nullable=false)
	private LocalTime openTime;
	
	@Column(nullable=false)
	private LocalTime closeTime;
//	
//	public boolean available() {
//		LocalTime currentTime = LocalTime.now();
//		
//		if(currentTime.isAfter(openTime) && currentTime.isBefore(closeTime)) return true;
//		
//		return false;
//	}
	
	
	public String openCloseTime() {
		
		return openTime+" ~ "+closeTime;
	}
	public LocalTime getOpenTime() {
		return openTime;
	}
	public LocalTime getCloseTime() {
		return closeTime;
	}
	public BusinessHour(String openTime,String closeTime) {
		this.openTime = LocalTime.parse(openTime);
		this.closeTime = LocalTime.parse(closeTime);
		
	}
	public BusinessHour() {
		
	}
	
	
}
