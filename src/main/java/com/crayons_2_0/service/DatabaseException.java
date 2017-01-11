package com.crayons_2_0.service;

public class DatabaseException extends Exception {
	
	private String reason = null;
	
	public DatabaseException(String reason) {
		this.setReason(reason);
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

}
