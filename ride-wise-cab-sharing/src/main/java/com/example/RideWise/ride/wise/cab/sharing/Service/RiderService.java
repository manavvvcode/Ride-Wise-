package com.example.RideWise.ride.wise.cab.sharing.Service;

import com.example.RideWise.ride.wise.cab.sharing.Dto.DeletedEntity;
import com.example.RideWise.ride.wise.cab.sharing.Dto.RiderDetailsDto;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Ride;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Entity.User;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Repository.RideRepository;
import com.example.RideWise.ride.wise.cab.sharing.Repository.RiderRepository;
import com.example.RideWise.ride.wise.cab.sharing.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RiderService {

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RideRepository rideRepository;

    public List<Rider> getAllRiders() {
        return riderRepository.findAll();
    }

    public RiderDetailsDto getRiderInfo(User customUser) throws RiderNotFoundException {
        Rider rider = riderRepository.findByUser(customUser);
        List<Ride> rides = rider.getRides();
        if (rides.isEmpty()) {
            return RiderDetailsDto.builder()
                    .id(rider.getId())
                    .firstName(rider.getFirstName())
                    .lastName(rider.getLastName())
                    .email(customUser.getEmail())
                    .completedRides(null)
                    .build();
        }
        return RiderDetailsDto.builder()
                .id(rider.getId())
                .firstName(rider.getFirstName())
                .lastName(rider.getLastName())
                .email(customUser.getEmail())
                .completedRides(rides)
                .build();
    }

//    @Transactional
//    public Rider createNewRider(Rider rider) throws RiderAlreadyExistsException {
//        if (!riderRepository.existsByEmail(rider.getEmail())) {
//            riderRepository.save(rider);
//        }
//        throw new RiderAlreadyExistsException("Rider already exists!");
//    }

    @Transactional
    public String deleteRider(String id) throws RiderNotFoundException {
        riderRepository.deleteByEmail(id);
        return "Rider deleted successfully";
    }
}
