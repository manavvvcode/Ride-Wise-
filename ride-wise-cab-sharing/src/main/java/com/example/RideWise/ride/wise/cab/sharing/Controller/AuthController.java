package com.example.RideWise.ride.wise.cab.sharing.Controller;

import com.example.RideWise.ride.wise.cab.sharing.Dto.DriverDto;
import com.example.RideWise.ride.wise.cab.sharing.Dto.LoginRequestDto;
import com.example.RideWise.ride.wise.cab.sharing.Dto.LoginResponseDto;
import com.example.RideWise.ride.wise.cab.sharing.Dto.RiderDto;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Driver;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.*;
import com.example.RideWise.ride.wise.cab.sharing.Service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/rider/signup")
    public ResponseEntity<Rider> registerNewRider(@RequestBody RiderDto rider) throws RiderAlreadyExistsException {
        return ResponseEntity.ok(authService.registerNewRider(rider));
    }

    @PostMapping(path = "/driver/signup")
    public ResponseEntity<Driver> registerNewDriver(@RequestBody DriverDto driver) throws DriverAlreadyExistsException {
        return ResponseEntity.ok(authService.registerNewDriver(driver));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto riderLoginRequest) throws BadCredentialsException {
        return ResponseEntity.ok(authService.login(riderLoginRequest));
    }
}
