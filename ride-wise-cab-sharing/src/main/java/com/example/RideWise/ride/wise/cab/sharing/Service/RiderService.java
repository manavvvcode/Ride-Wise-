package com.example.RideWise.ride.wise.cab.sharing.Service;

import com.example.RideWise.ride.wise.cab.sharing.Dto.DeletedEntity;
import com.example.RideWise.ride.wise.cab.sharing.Dto.RiderDetailsDto;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Ride;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Entity.User;
import com.example.RideWise.ride.wise.cab.sharing.Enum.Role;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.DriverNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Repository.RideRepository;
import com.example.RideWise.ride.wise.cab.sharing.Repository.RiderRepository;
import com.example.RideWise.ride.wise.cab.sharing.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RiderService {

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private UserRepository userRepository;

   // @Autowired
   // private RideRepository rideRepository;

    public List<Rider> getAllRiders() {
        return riderRepository.findAll();
    }

    public RiderDetailsDto getRiderInfo(User customUser) throws RiderNotFoundException {
        Rider rider = riderRepository.findByUser(customUser).orElseThrow(() -> new RiderNotFoundException("rider with email " + customUser.getEmail() + " not found"));
        if (rider.getRides().isEmpty()) {
            return RiderDetailsDto.builder()
                    .id(rider.getId())
                    .firstName(rider.getFirstName())
                    .lastName(rider.getLastName())
                    .email(customUser.getEmail())
                    .completedRides(null)
                    .memberSince(rider.getMemberSince().getYear())
                    .build();
        }
        return RiderDetailsDto.builder()
                .id(rider.getId())
                .firstName(rider.getFirstName())
                .lastName(rider.getLastName())
                .email(customUser.getEmail())
                .completedRides(rider.getRides())
                .memberSince(rider.getMemberSince().getYear())
                .build();
    }

    @Transactional
    public String deleteRider(User customUser) throws RiderNotFoundException {
        Rider rider = userRepository.findByUser(customUser).orElseThrow(() -> new RiderNotFoundException("rider with email " + customUser.getEmail() + " not found"));
        User user = rider.getUser();
        if (user.getRole().contains(Role.DRIVER)) {
            user.getRole().remove(Role.RIDER);
            userRepository.save(user);
            riderRepository.delete(rider);
            return "Rider deleted successfully!";
        }
        userRepository.delete(user);
        riderRepository.delete(rider);
        return "Rider deleted successfully!";
    }
}
