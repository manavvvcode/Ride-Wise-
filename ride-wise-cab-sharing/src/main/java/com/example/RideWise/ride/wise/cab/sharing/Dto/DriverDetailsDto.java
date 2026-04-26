package com.example.RideWise.ride.wise.cab.sharing.Dto;

import com.example.RideWise.ride.wise.cab.sharing.Entity.Ride;
import com.example.RideWise.ride.wise.cab.sharing.Enum.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverDetailsDto {
    private Long id;
    private String FirstName;
    private String LastName;
    private boolean AvailabilityStatus;
    private VehicleType vehicleType;
    private int totalRidesCompleted;
    private List<Ride> rides;
    private Integer memberSince;
}
