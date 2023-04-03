package com.reservation.foodTable.dto;

import java.time.LocalTime;



public class AvailabilityData {
	
	private Integer id;
	
	private LocalTime time;
	
	private int remainingSeats;
	
	
	public AvailabilityData(Integer id,LocalTime time) {
	      super();
	      this.id = id;
	      this.time = time;
	   }
	
	public LocalTime getTime() {
		return time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public int getRemainingSeats() {
		return remainingSeats;
	}

	public void setRemainingSeats(int remainingSeats) {
		this.remainingSeats = remainingSeats;
	}

	public AvailabilityData(Integer id,LocalTime time, int remainingSeats) {
		super();
		this.id = id;
		this.time = time;
		this.remainingSeats = remainingSeats;
	}
	
	public AvailabilityData() {
		
	}

	public boolean isAm() {
		
		return time.isBefore(LocalTime.of(12, 0));
	}
	public void convert12Houre() {
		if(this.time.isBefore(LocalTime.of(13, 0))) return;
		
		time.minusHours(12);
	}
	
	public boolean isTableUnavailable() {
	    return remainingSeats <= 0;
	}

	@Override
	public String toString() {
		return "AvailabilityData [id=" + id + ", time=" + time + ", remainingSeats=" + remainingSeats + "]";
	}
	
	
}
