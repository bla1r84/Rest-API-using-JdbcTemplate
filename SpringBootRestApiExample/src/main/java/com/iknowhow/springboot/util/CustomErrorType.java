package com.iknowhow.springboot.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

public class CustomErrorType {

	private HttpStatus status;
    private String errorMessage;
    private List<String> errors;

   
    public CustomErrorType(HttpStatus status, String errorMessage, List<String> errors) {
		super();
		this.status = status;
		this.errorMessage = errorMessage;
		this.errors = errors;
	}



    public CustomErrorType(HttpStatus status, String errorMessage, String error) {
		super();
		this.status = status;
		this.errorMessage = errorMessage;
		this.errors = Arrays.asList(error);
	}



	public HttpStatus getStatus() {
		return status;
	}



	public void setStatus(HttpStatus status) {
		this.status = status;
	}



	public String getErrorMessage() {
		return errorMessage;
	}



	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}



	public List<String> getErrors() {
		return errors;
	}



	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
    
}
