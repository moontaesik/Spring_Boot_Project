package com.reservation.foodTable.exception;

/*
 * 레스토랑 페이지로 이동하는 요청이 올때 해당 요청의 id 에 해당하는 레스토랑이 없는경우 반환할 Exception
 * */
public class NotFoundRestaurantException extends Exception {
	public NotFoundRestaurantException() {
		
		super("요청하신 레스토랑의 정보가 없습니다.");
	}	
	
	public NotFoundRestaurantException(String message) {
		super(message);
	}
}