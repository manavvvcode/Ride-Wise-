package com.example.RideWise.ride.wise.cab.sharing.Dto;

import com.example.RideWise.ride.wise.cab.sharing.Entity.Ride;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RiderDetailsDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Ride> completedRides;
    private Integer memberSince;
}
