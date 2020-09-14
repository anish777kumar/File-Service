package com.bunnu.exception;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class GlobalExceptionHandler.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	
	/**
	 * Handle file not found exception.
	 *
	 * @param fnfe the fnfe
	 * @return the response entity
	 */
	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> handleFileNotFoundException(FileNotFoundException fnfe)
	{
		ExceptionResponse er = new ExceptionResponse();
		er.setStatus(HttpStatus.NOT_FOUND.value());
		er.setExceptionMessage(fnfe.getClass().getCanonicalName()+" : "+fnfe.getMessage());
		er.setDate(LocalDateTime.now());
		
		log.error("FileNotFoundException :"+fnfe.getMessage());
		fnfe.printStackTrace();
		return new ResponseEntity<ExceptionResponse>(er, HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handle document storage exception.
	 *
	 * @param dse the dse
	 * @return the response entity
	 */
	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> handleDocumentStorageException(DocumentStorageException dse)
	{
		ExceptionResponse er = new ExceptionResponse();
		er.setStatus(HttpStatus.BAD_REQUEST.value());
		er.setExceptionMessage(dse.getClass().getCanonicalName()+" : "+dse.getMessage());
		er.setDate(LocalDateTime.now());
		
		log.error("DocumentStorageException :"+dse.getMessage());
		dse.printStackTrace();
		return new ResponseEntity<ExceptionResponse>(er, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Handle un authorised operation.
	 *
	 * @param unex the unex
	 * @return the response entity
	 */
	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> handleUnAuthorisedOperation(UnAuthorisedOperationException unex)
	{
		ExceptionResponse er = new ExceptionResponse();
		er.setStatus(HttpStatus.UNAUTHORIZED.value());
		er.setExceptionMessage(unex.getClass().getCanonicalName()+" : "+unex.getMessage());
		er.setDate(LocalDateTime.now());
		
		log.error("DocumentStorageException :"+unex.getMessage());
		unex.printStackTrace();
		return new ResponseEntity<ExceptionResponse>(er, HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	/**
	 * Handle all exception.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> handleAllException(Exception ex)
	{
		ExceptionResponse er = new ExceptionResponse();
		er.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		er.setExceptionMessage(ex.getClass().getCanonicalName()+" : "+ex.getMessage());
		er.setDate(LocalDateTime.now());
		
		log.error("Exception :"+ex.getMessage());
		ex.printStackTrace();
		return new ResponseEntity<ExceptionResponse>(er, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
