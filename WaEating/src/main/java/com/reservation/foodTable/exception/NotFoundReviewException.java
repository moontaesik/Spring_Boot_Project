package com.reservation.foodTable.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundReviewException extends Exception {

	public NotFoundReviewException() {
		super();
	}
	
	public NotFoundReviewException(String message) {
		super(message);
	}
}
