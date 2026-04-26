package com.example.RideWise.ride.wise.cab.sharing.Repository;

import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<Rider,Long> {
   // void deleteByEmail(String id);

    Optional<Rider> findByUser(User customUser);

    void deleteByEmail(String email);
    //boolean existsByEmail(@Email String email);

    //Optional<Rider> findByEmail(String email);
}
