package com.example.RideWise.ride.wise.cab.sharing.Dto;

import com.example.RideWise.ride.wise.cab.sharing.Entity.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideDetailsDto {
    private Long id;
    private String riderName;
    private String driverName;
    private Double fare;
    private Location pickup;
    private Location drop;
    private Double distance;
}
