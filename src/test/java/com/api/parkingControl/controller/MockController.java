package com.api.parkingControl.controller;

import com.api.parkingControl.dto.ParkingSpotDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MockController {

    public static String getParkingSpotDTO() {
        return asJsonString(ParkingSpotDTO.builder()
                ._id("646ec4d37827756a2b8adfd8")
                .parkingSpotNumber("39")
                .licensePlateCar("XYZ3U39")
                .brandCar("CHEVROLET")
                .modelCar("ONIX")
                .colorCar("BLUE")
                .responsibleName("Franzim")
                .apartment("139")
                .block("C")
                .build());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
