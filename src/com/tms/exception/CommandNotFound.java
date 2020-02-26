package com.tms.exception;

public class CommandNotFound extends TMSException {
	
	private static final long serialVersionUID = 1L;

	public CommandNotFound(String message) {
		super(message, "TMS:1");
		
	}
	
}