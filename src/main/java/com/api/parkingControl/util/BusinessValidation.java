package com.api.parkingControl.util;

import com.api.parkingControl.dto.ParkingSpotDTO;
import com.api.parkingControl.repository.ParkingSpotRepository;
import org.springframework.stereotype.Service;

@Service
public class BusinessValidation {

    private final ParkingSpotRepository parkingSpotRepository;

    public BusinessValidation(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public String spotValidate(ParkingSpotDTO parkingSpotDTO) {
        if(existsByLicensePlateCar(parkingSpotDTO.getLicensePlateCar())) {
            return "Conflict: License Plate Car is already in use!";
        }
        if(existsByParkingSpotNumber(parkingSpotDTO.getParkingSpotNumber())) {
            return "Conflict: Parking Spot is already in use!";
        }
        if(existsByApartmentAndBlock(parkingSpotDTO.getApartment(), parkingSpotDTO.getBlock())) {
            return "Conflict: Apartment and block is already in use!";
        }
        return null;
    }

    private boolean existsByLicensePlateCar(String licensePlateCar) {
        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }

    private boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    private boolean existsByApartmentAndBlock(String apartment, String block) {
        return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
    }
}