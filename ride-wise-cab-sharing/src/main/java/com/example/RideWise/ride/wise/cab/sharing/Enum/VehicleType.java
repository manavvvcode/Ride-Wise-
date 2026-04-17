package com.example.RideWise.ride.wise.cab.sharing.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum VehicleType {
    BIKE, AUTO, CAR;


    @JsonCreator
    public static VehicleType from(String value) {
        return VehicleType.valueOf(value.toUpperCase());
    }

    }
