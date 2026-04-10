package com.example.RideWise.ride.wise.cab.sharing.Repository;

import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiderRepository extends JpaRepository<Rider,Long> {
    boolean existsByEmail(@Email String email);
}
