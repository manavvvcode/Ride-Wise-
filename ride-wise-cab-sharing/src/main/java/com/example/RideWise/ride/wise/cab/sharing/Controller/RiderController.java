package com.example.RideWise.ride.wise.cab.sharing.Controller;

import com.example.RideWise.ride.wise.cab.sharing.Dto.DeletedEntity;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rider")
public class RiderController {

    @Autowired
    private RiderService riderService;

    @GetMapping
    public ResponseEntity<List<Rider>> getALlRiders(){
        return ResponseEntity.status(200).body(riderService.getAllRiders());
    }

    @GetMapping(path = "/{riderId}")
    public ResponseEntity<Rider> getRiderById(@PathVariable(name = "riderId")Long id) throws RiderNotFoundException {
        return ResponseEntity.status(200).body(riderService.getRiderById(id));
    }
//     setting up auth config so no longer required now
//    @PostMapping
//    public ResponseEntity<Rider> createNewRider(@RequestBody Rider rider) throws RiderAlreadyExistsException {
//        return ResponseEntity.status(200).body(riderService.createNewRider(rider));
//    }

    @DeleteMapping(path = "/{riderId}")
    public ResponseEntity<DeletedEntity<?>> deleteRider(@PathVariable(value = "riderId")Long id) throws RiderNotFoundException {
        return ResponseEntity.status(200).body(riderService.deleteRider(id));
    }

}
