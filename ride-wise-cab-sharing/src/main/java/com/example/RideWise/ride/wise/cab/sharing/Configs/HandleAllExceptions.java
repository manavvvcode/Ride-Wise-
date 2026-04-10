package com.example.RideWise.ride.wise.cab.sharing.Configs;

import com.example.RideWise.ride.wise.cab.sharing.Dto.ApiErrorResponse;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.DriverAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.DriverNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class HandleAllExceptions {

    @ExceptionHandler(RiderNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> riderNotFound(RiderNotFoundException ex) {
        return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RiderAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> riderExists(RiderAlreadyExistsException ex) {
        return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(DriverNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> driverNotFound(DriverNotFoundException ex) {
        return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DriverAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> driverExists(DriverAlreadyExistsException ex) {
        return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
