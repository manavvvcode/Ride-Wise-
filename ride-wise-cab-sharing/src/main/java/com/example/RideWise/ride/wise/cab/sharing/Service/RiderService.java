package com.example.RideWise.ride.wise.cab.sharing.Service;

import com.example.RideWise.ride.wise.cab.sharing.Dto.DeletedEntity;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Ride;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Repository.RiderRepository;
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

    public List<Rider> getAllRiders() {
        return riderRepository.findAll();
    }

    public Rider getRiderById(Long id) throws RiderNotFoundException {
        return riderRepository.findById(id).orElseThrow(() -> new RiderNotFoundException("Rider with id " + id + " not found."));
    }

//    @Transactional
//    public Rider createNewRider(Rider rider) throws RiderAlreadyExistsException {
//        if (!riderRepository.existsByEmail(rider.getEmail())) {
//            riderRepository.save(rider);
//        }
//        throw new RiderAlreadyExistsException("Rider already exists!");
//    }

    @Transactional
    public DeletedEntity<?> deleteRider(Long id) throws RiderNotFoundException {
        Optional<Rider> riderOptional = riderRepository.findById(id);
        if (riderOptional.isEmpty()) {
            throw new RiderNotFoundException("Rider with id " + id + " not found.");
        }
        riderRepository.deleteById(id);
        List<Rider> updatedRiderList = riderRepository.findAll();
        return new DeletedEntity<>("rider deleted successfully", updatedRiderList);
    }
}
