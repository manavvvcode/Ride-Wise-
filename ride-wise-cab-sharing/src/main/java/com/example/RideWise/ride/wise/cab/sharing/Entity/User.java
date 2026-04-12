package com.example.RideWise.ride.wise.cab.sharing.Entity;

import com.example.RideWise.ride.wise.cab.sharing.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Email
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
