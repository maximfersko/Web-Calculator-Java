package com.edu.fersko.smartcalc.exceptions;

import com.edu.fersko.smartcalc.models.type.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NativeCalculationException.class)
	public ResponseEntity<ResultResponse> handleNativeCalculationException(NativeCalculationException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultResponse("Error during calculation"));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResultResponse> handleOtherExceptions(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultResponse("Internal server error"));
	}
}
