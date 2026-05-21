package com.example.RideWise.ride.wise.cab.sharing.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Wallet {
    private Long id;
    private Double balance;
    @OneToOne
    private User user;
}
