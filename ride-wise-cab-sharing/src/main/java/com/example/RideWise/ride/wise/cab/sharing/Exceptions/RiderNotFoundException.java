package com.example.RideWise.ride.wise.cab.sharing.Exceptions;

import org.springframework.web.bind.annotation.RestControllerAdvice;


public class RiderNotFoundException extends Exception {

    public RiderNotFoundException(String message) {
        super(message);
    }
}
