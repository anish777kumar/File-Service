package com.bunnu.exception;

import java.time.LocalDateTime;

// TODO: Auto-generated Javadoc
/**
 * The Class ExceptionResponse.
 */
public class ExceptionResponse {
	
	/** The status. */
	int status;
	
	/** The exception message. */
	String exceptionMessage;
	
	/** The date. */
	LocalDateTime date;
	
	/**
	 * Instantiates a new exception response.
	 */
	public ExceptionResponse() {
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Gets the exception message.
	 *
	 * @return the exception message
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	/**
	 * Sets the exception message.
	 *
	 * @param exceptionMessage the new exception message
	 */
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	

}
