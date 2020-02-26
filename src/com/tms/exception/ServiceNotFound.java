package com.tms.exception;

public class ServiceNotFound extends TMSException {

	private static final long serialVersionUID = 1L;

	public ServiceNotFound(String message) {
		super(message, "TMS:7");
	}

}
