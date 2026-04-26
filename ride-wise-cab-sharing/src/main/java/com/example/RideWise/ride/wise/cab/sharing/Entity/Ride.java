package com.example.RideWise.ride.wise.cab.sharing.Entity;

import com.example.RideWise.ride.wise.cab.sharing.Enum.RideStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "pickup_lat")),
            @AttributeOverride(name = "longitude", column = @Column(name = "pickup_lng"))
    })
    private Location pickupLocation;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "dest_lat")),
            @AttributeOverride(name = "longitude", column = @Column(name = "dest_lng"))
    })
    private Location destinationLocation;

    @Transient
    private Double distance;

    private Double fare;

    private RideStatus status;

    @OneToOne(mappedBy = "ride", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private FareReceipt fareReceipt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ride ride = (Ride) o;
        return Objects.equals(id, ride.id) && Objects.equals(driver, ride.driver) && Objects.equals(rider, ride.rider) && Objects.equals(pickupLocation, ride.pickupLocation) && Objects.equals(destinationLocation, ride.destinationLocation) && Objects.equals(distance, ride.distance) && Objects.equals(fare, ride.fare) && status == ride.status && Objects.equals(fareReceipt, ride.fareReceipt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, driver, rider, pickupLocation, destinationLocation, distance, fare, status, fareReceipt);
    }
}
