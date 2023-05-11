package com.api.parkingControl.repository;

import com.api.parkingControl.model.ParkingSpot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ParkingSpotRepository extends MongoRepository<ParkingSpot, String> {
    @Transactional
    ParkingSpot save(ParkingSpot parkingSpot);

    Boolean existsByLicensePlateCar(String licensePlateCar);

    Boolean existsByParkingSpotNumber(String parkingSpotNumber);

    Boolean existsByApartmentAndBlock(String apartmentAndBlock, String block);
}