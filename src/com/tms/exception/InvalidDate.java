package com.tms.exception;

public class InvalidDate extends TMSException {

	private static final long serialVersionUID = 1L;

	public InvalidDate(String message) {
		super(message, "TMS:5");
	}

}
