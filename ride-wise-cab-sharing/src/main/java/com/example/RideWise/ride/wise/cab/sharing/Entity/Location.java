package com.example.RideWise.ride.wise.cab.sharing.Entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Location {

    private Double latitude;
    private Double longitude;
}
