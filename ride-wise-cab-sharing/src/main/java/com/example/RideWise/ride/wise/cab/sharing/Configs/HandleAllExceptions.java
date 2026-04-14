package com.example.RideWise.ride.wise.cab.sharing.Configs;

import com.example.RideWise.ride.wise.cab.sharing.Dto.ApiErrorResponse;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class HandleAllExceptions {

    @ExceptionHandler(RiderNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> riderNotFound(RiderNotFoundException ex) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .subErrors(null)
                .build();
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }

    @ExceptionHandler(RiderAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> riderExists(RiderAlreadyExistsException ex) {
        return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DriverNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> driverNotFound(DriverNotFoundException ex) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .subErrors(null)
                .build();
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }

    @ExceptionHandler(DriverAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> driverExists(DriverAlreadyExistsException ex) {
        return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> badCredentials(BadCredentialsException ex) {
        return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> generalException(Exception ex) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .errorMessage("Some unexpected error occurred : " + ex.getMessage())
                .subErrors(null)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }

    @ExceptionHandler(UserNameNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> userNameNotFoundException(UserNameNotFoundException ex) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .subErrors(null)
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }

}
