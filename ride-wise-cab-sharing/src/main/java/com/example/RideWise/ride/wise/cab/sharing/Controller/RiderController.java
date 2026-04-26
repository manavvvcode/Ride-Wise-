package com.example.RideWise.ride.wise.cab.sharing.Controller;

import com.example.RideWise.ride.wise.cab.sharing.Dto.DeletedEntity;
import com.example.RideWise.ride.wise.cab.sharing.Dto.RiderDetailsDto;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Entity.User;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Security.CustomUserDetailsService;
import com.example.RideWise.ride.wise.cab.sharing.Service.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@PreAuthorize("hasRole('RIDER')")
@RestController
@RequestMapping(path = "/rider")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;

    @GetMapping(path = "/me")
    public ResponseEntity<RiderDetailsDto> riderInfo(@AuthenticationPrincipal User customUser) throws RiderNotFoundException {
        return ResponseEntity.status(200).body(riderService.getRiderInfo(customUser));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> deleteAccount(@AuthenticationPrincipal User customUser) throws RiderNotFoundException {
        return ResponseEntity.status(200).body(riderService.deleteRider(customUser));
    }

}
