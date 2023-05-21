package com.api.parkingControl.util;

import com.api.parkingControl.dto.ParkingSpotDTO;
import com.api.parkingControl.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BusinessValidation {

    @Autowired
    private static ParkingSpotRepository parkingSpotRepository;

    public static String spotValidate(ParkingSpotDTO parkingSpotDTO) {
        if(existsByLicensePlateCar(parkingSpotDTO.getLicensePlateCar())) {
            return "Conflict: License Plate Car is already in use!";
        }
        if(existsByParkingSpotNumber(parkingSpotDTO.getParkingSpotNumber())) {
            return "Conflict: Parking Spot is already in use!";
        }
        if(existsByApartmentAndBlock(parkingSpotDTO.getApartment(), parkingSpotDTO.getBlock())) {
            return "Conflict: License Plate Car is already in use!";
        }
        return null;
    }

    private static boolean existsByLicensePlateCar(String licensePlateCar) {
        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }

    private static boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    private static boolean existsByApartmentAndBlock(String apartment, String block) {
        return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
    }
}