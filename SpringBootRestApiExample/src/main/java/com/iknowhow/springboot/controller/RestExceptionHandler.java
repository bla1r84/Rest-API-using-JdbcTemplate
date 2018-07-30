package com.iknowhow.springboot.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.iknowhow.springboot.util.CustomErrorType;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		final String errorMessage = "Parameters missing or invalid!";
		CustomErrorType customErrorType = new CustomErrorType(HttpStatus.UNPROCESSABLE_ENTITY, ex.getLocalizedMessage(), errorMessage);
		return new ResponseEntity<Object>(customErrorType, new HttpHeaders(), customErrorType.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request! Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t-> builder.append(t + " "));
		CustomErrorType customErrorType = new CustomErrorType(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());
		return new ResponseEntity<Object>(customErrorType, new HttpHeaders(), customErrorType.getStatus());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public CustomErrorType handleEmptyResultDataAccessException(Exception e, WebRequest request) {
		return new CustomErrorType(HttpStatus.NOT_FOUND, "No user found with that ID!", e.getMessage());
	}
}