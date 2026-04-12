package com.example.RideWise.ride.wise.cab.sharing.Service;

import com.example.RideWise.ride.wise.cab.sharing.Dto.DeletedEntity;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Driver;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.DriverAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.DriverNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Repository.DriverRepository;
import com.example.RideWise.ride.wise.cab.sharing.Repository.RiderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver getDriverById(Long id) throws DriverNotFoundException {
        return driverRepository.findById(id).orElseThrow(() -> new DriverNotFoundException("Rider with id " + id + " not found."));
    }

//    @Transactional
//    public Driver createNewDriver(Driver driver) throws DriverAlreadyExistsException {
//        if (!driverRepository.existsByEmail(driver.getEmail())) {
//            driverRepository.save(driver);
//        }
//        throw new DriverAlreadyExistsException("Rider already exists!");
//    }

    @Transactional
    public DeletedEntity<?> deleteDriver(Long id) throws DriverNotFoundException {
        Optional<Driver> driverOptional = driverRepository.findById(id);
        if (driverOptional.isEmpty()) {
            throw new DriverNotFoundException("Driver with id " + id + " not found.");
        }
        driverRepository.deleteById(id);
        List<Driver> updatedDriverList = driverRepository.findAll();
        //int x = Integer.parseInt("123");
        return new DeletedEntity<>("Driver deleted successfully", updatedDriverList);
    }
}
