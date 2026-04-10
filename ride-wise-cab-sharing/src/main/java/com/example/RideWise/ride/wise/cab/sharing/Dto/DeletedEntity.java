package com.example.RideWise.ride.wise.cab.sharing.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeletedEntity<T> {
    private String message;
    List<T> updatedListOfEntity;
}
