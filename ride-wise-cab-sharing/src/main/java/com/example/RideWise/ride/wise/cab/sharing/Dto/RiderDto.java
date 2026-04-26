package com.example.RideWise.ride.wise.cab.sharing.Dto;

import com.example.RideWise.ride.wise.cab.sharing.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
//    private List<Role> roles;
}
