package com.example.RideWise.ride.wise.cab.sharing.Dto;

import com.example.RideWise.ride.wise.cab.sharing.Entity.Location;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Ride;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
public class FareReceiptDto {

    private long id;

    private Double amount;

    private String driverName;

    private Location pickupLocation;
    private Location destinationLocation;

    private LocalDateTime createdAt;
}
