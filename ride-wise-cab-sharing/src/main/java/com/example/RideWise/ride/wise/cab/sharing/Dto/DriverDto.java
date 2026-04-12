package com.example.RideWise.ride.wise.cab.sharing.Dto;

import com.example.RideWise.ride.wise.cab.sharing.Enum.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {
    private String firstName;
    private String lastName;
    private VehicleType vehicleType;
    private boolean isAvailable;
    private String email;
    private String password;
}
