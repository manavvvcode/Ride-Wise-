package com.example.RideWise.ride.wise.cab.sharing.Controller;

import com.example.RideWise.ride.wise.cab.sharing.Dto.DeletedEntity;
import com.example.RideWise.ride.wise.cab.sharing.Dto.RiderDetailsDto;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Entity.User;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Security.CustomUserDetailsService;
import com.example.RideWise.ride.wise.cab.sharing.Service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@PreAuthorize("hasRole('RIDER')")
@RestController
@RequestMapping(path = "/rider")
public class RiderController {

    @Autowired
    private RiderService riderService;

//    @GetMapping
//    public ResponseEntity<List<Rider>> getALlRiders() {
//        return ResponseEntity.status(200).body(riderService.getAllRiders());
//    }

    @GetMapping(path = "/me")
    public ResponseEntity<RiderDetailsDto> riderInfo(@AuthenticationPrincipal User customUser) throws RiderNotFoundException {
        return ResponseEntity.status(200).body(riderService.getRiderInfo(customUser));
    }

    @DeleteMapping(path = "/{riderEmail}")
    public ResponseEntity<String> deleteAccount(@PathVariable(value = "riderEmail") String email,
                                                @AuthenticationPrincipal User customUser) throws RiderNotFoundException {
        if (!customUser.getEmail().equals(email)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.status(200).body(riderService.deleteRider(email));
    }

}
