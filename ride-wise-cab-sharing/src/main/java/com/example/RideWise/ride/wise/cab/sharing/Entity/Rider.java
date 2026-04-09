package com.example.RideWise.ride.wise.cab.sharing.Entity;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "rider_tbl"
)
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(unique = true)
    @Email
    private String email;

    @Embedded
    private Location location;

    @OneToMany(mappedBy = "rider")
    private List<Ride> rider_rides;


}
