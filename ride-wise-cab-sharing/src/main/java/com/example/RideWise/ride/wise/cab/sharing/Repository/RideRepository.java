package com.example.RideWise.ride.wise.cab.sharing.Repository;

import com.example.RideWise.ride.wise.cab.sharing.Entity.Driver;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<Ride,Long> {
    //boolean existsByEmail(@Email String email);
}
