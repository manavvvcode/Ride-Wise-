package com.example.RideWise.ride.wise.cab.sharing.Service;

import com.example.RideWise.ride.wise.cab.sharing.Dto.DriverDto;
import com.example.RideWise.ride.wise.cab.sharing.Dto.LoginRequestDto;
import com.example.RideWise.ride.wise.cab.sharing.Dto.LoginResponseDto;
import com.example.RideWise.ride.wise.cab.sharing.Dto.RiderDto;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Driver;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Entity.User;
import com.example.RideWise.ride.wise.cab.sharing.Enum.Role;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.DriverAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Repository.DriverRepository;
import com.example.RideWise.ride.wise.cab.sharing.Repository.RiderRepository;
import com.example.RideWise.ride.wise.cab.sharing.Repository.UserRepository;
import com.example.RideWise.ride.wise.cab.sharing.Security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RiderRepository riderRepository;
    private final DriverRepository driverRepository;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public Rider registerNewRider(RiderDto rider) throws RiderAlreadyExistsException {
        User Optionaluser = userRepository.findByEmail(rider.getEmail()).orElse(null);
        if (Optionaluser != null) {
            throw new RiderAlreadyExistsException("you already have an existing account with gmail " + rider.getEmail());
        }
        User user = User.builder()
                .email(rider.getEmail())
                .password(passwordEncoder.encode(rider.getPassword()))
                .role(Role.RIDER)
                .build();
        userRepository.save(user);

        Rider toBeSavedRider = Rider.builder()
                .user(user)
                .firstName(rider.getFirstName())
                .lastName(rider.getLastName())
                .build();
        return riderRepository.save(toBeSavedRider);
    }


    @Transactional
    public LoginResponseDto loginRider(LoginRequestDto riderLoginRequest) throws RiderNotFoundException {
        User optionalUser = userRepository.findByEmail(riderLoginRequest.getEmail()).orElse(null);
        if (optionalUser == null) {
            throw new RiderNotFoundException("you don't have an account, kindly register yourself!");
        }
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(riderLoginRequest.getEmail(), riderLoginRequest.getPassword()));

        User user = (User) auth.getPrincipal();
        if (user != null) {
            String token = JwtUtil.generateToken(user);
            return new LoginResponseDto(token, user.getId());
        }
        throw new RuntimeException("sorry couldn't validate your identity!");
    }

    public Driver registerNewDriver(DriverDto driver) throws DriverAlreadyExistsException {
        User userOptional = userRepository.findByEmail(driver.getEmail()).orElse(null);
        if (userOptional != null) {
            throw new DriverAlreadyExistsException("you already have an existing account with gmail " + driver.getEmail());
        }
        User user = User.builder()
                .email(driver.getEmail())
                .password(passwordEncoder.encode(driver.getPassword()))
                .role(Role.DRIVER)
                .build();
        userRepository.save(user);
        Driver toBeSavedDriver = Driver.builder()
                .FirstName(driver.getFirstName())
                .LastName(driver.getLastName())
                .vehicleType(driver.getVehicleType())
                .isAvailable(driver.isAvailable())
                .build();
        return driverRepository.save(toBeSavedDriver);
    }
}
