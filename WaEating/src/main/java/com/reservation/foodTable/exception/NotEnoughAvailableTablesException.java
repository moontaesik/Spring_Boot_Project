package com.reservation.foodTable.exception;

public class NotEnoughAvailableTablesException extends Exception {

	public NotEnoughAvailableTablesException() {
		super();
	}
	
	public NotEnoughAvailableTablesException(String message) {
		super(message);
	}
}
