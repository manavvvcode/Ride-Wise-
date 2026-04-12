package com.example.RideWise.ride.wise.cab.sharing.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
