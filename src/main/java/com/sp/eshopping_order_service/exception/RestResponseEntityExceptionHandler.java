package com.sp.eshopping_order_service.exception;

import com.sp.eshopping_order_service.payload.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(OrderServiceCustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(OrderServiceCustomException exception){

        ErrorResponse error=new ErrorResponse(exception.getMessage(),exception.getErrorCode());
        return new ResponseEntity<>(error, HttpStatus.valueOf(exception.getStatus()));
    }
}
