package com.example.RideWise.ride.wise.cab.sharing.Entity;

import com.example.RideWise.ride.wise.cab.sharing.Enum.VehicleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.processing.Pattern;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 50)
    private String FirstName;

    @Column(name = "last_name", length = 50)
    private String LastName;

    private boolean AvailableStatus;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(name = "rides_completed")
    private int totalRidesCompleted = 0;

    @OneToOne
    private User user;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "current_lat")),
            @AttributeOverride(name = "longitude", column = @Column(name = "current_long"))
    })
    private Location currentLocation;

    @OneToMany(mappedBy = "rider", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Ride> rides;
}
