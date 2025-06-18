package com.tracker.backend.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tracker.backend.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException resourceExcpt){
		String messageString = resourceExcpt.getMessage();
		ApiResponse apiResponse = new ApiResponse(messageString, false);
		
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException excpt){
		Map<String, String> response = new HashMap<>();
		
		excpt.getBindingResult().getAllErrors().forEach( (error) -> {
			String fieldName =((FieldError)error).getField();
			String message = error.getDefaultMessage();
			response.put(fieldName, message);
		});
		
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> apiExceptionHandler(ApiException apiException){
		String messageString = apiException.getMessage();
		ApiResponse apiResponse = new ApiResponse(messageString, false);
		
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}
}
