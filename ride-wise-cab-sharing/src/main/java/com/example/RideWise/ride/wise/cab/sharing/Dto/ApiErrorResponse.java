package com.example.RideWise.ride.wise.cab.sharing.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {

    private HttpStatus status;
    private String errorMessage;
    private List<String> subErrors;
}
