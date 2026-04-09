package com.example.RideWise.ride.wise.cab.sharing.Entity;

import com.example.RideWise.ride.wise.cab.sharing.Enum.RideStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Driver driver;

    @ManyToOne
    private Rider rider;

    @Embedded
    private Location pickupLocation;

    @Embedded
    private Location destinationLocation;

    @Transient
    private Double distance;

    private Double fare;

    private RideStatus status;

    @OneToOne(mappedBy = "ride", cascade = CascadeType.ALL, orphanRemoval = true)
    private FareReceipt fareReceipt;
}
