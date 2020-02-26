package com.tms.exception;

public class InvalidCategoryForType extends TMSException {

	private static final long serialVersionUID = 1L;

	public InvalidCategoryForType(String message) {
		super(message, "TMS:4");
	}

}
