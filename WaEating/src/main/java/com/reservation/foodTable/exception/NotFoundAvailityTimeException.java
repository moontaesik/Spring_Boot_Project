package com.reservation.foodTable.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * 해당 날짜에 이용 가능한 시간대가 존재하지 않음을 나타내주는 에러
 * */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundAvailityTimeException extends Exception {
	
	public NotFoundAvailityTimeException() {
		
		super();
	}	
	
	public NotFoundAvailityTimeException(String message) {
		super(message+"에 이용가능한 시간이 없습니다.");
	}
}