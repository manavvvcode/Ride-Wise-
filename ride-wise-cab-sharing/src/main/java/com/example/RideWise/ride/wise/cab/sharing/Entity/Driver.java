package com.example.RideWise.ride.wise.cab.sharing.Entity;

import com.example.RideWise.ride.wise.cab.sharing.Enum.VehicleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.processing.Pattern;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name",length = 50)
    private String FirstName;

    @Column(name="last_name",length = 50)
    private String LastName;

    @Column(unique = true)
    @Email
    private String email;

    private boolean isAvailable;

    private VehicleType vehicleType;

    @Embedded
    @Column(name = "rider_location")
    private Location location;
    
    @Column(name = "rides_completed")
    private int totalRidesCompleted;

    @OneToMany(mappedBy = "driver")
    private List<Ride> driver_rides;
}
