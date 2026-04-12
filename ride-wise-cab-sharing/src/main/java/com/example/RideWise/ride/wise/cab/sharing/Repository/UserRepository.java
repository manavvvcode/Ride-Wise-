package com.example.RideWise.ride.wise.cab.sharing.Repository;

import com.example.RideWise.ride.wise.cab.sharing.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
