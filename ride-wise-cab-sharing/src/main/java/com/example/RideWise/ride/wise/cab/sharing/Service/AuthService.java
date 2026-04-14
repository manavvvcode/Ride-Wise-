package com.example.RideWise.ride.wise.cab.sharing.Service;

import com.example.RideWise.ride.wise.cab.sharing.Dto.DriverDto;
import com.example.RideWise.ride.wise.cab.sharing.Dto.LoginRequestDto;
import com.example.RideWise.ride.wise.cab.sharing.Dto.LoginResponseDto;
import com.example.RideWise.ride.wise.cab.sharing.Dto.RiderDto;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Driver;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Entity.User;
import com.example.RideWise.ride.wise.cab.sharing.Enum.Role;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.*;
import com.example.RideWise.ride.wise.cab.sharing.Repository.DriverRepository;
import com.example.RideWise.ride.wise.cab.sharing.Repository.RiderRepository;
import com.example.RideWise.ride.wise.cab.sharing.Repository.UserRepository;
import com.example.RideWise.ride.wise.cab.sharing.Security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RiderRepository riderRepository;
    private final DriverRepository driverRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Transactional
    public Rider registerNewRider(RiderDto rider) throws RiderAlreadyExistsException {
        User Optionaluser = userRepository.findByEmail(rider.getEmail()).orElse(null);
        if (Optionaluser != null) {
            throw new RiderAlreadyExistsException("you already have an existing account with email " + rider.getEmail() + " Kindly login using your registered email");
        }
        User user = User.builder()
                .email(rider.getEmail())
                .password(passwordEncoder.encode(rider.getPassword()))
                .role(List.of(Role.RIDER))
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
    public LoginResponseDto login(LoginRequestDto loginRequest) throws BadCredentialsException {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            User user = (User) auth.getPrincipal();
            String token = jwtUtil.generateToken(user);
            return new LoginResponseDto(token, user.getId());
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Transactional
    public Driver registerNewDriver(DriverDto driver) throws DriverAlreadyExistsException {
        User userOptional = userRepository.findByEmail(driver.getEmail()).orElse(null);
        if (userOptional != null) {
            throw new DriverAlreadyExistsException("you already have an existing account with gmail " + driver.getEmail());
        }
        User user = User.builder()
                .email(driver.getEmail())
                .password(passwordEncoder.encode(driver.getPassword()))
                .role(List.of(Role.DRIVER))
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
