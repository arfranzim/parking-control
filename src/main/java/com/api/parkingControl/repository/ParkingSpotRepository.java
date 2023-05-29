package com.api.parkingControl.repository;

import com.api.parkingControl.model.ParkingSpot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ParkingSpotRepository extends MongoRepository<ParkingSpot, String> {

    @SuppressWarnings({"unchecked", "NullableProblems"})
    @Transactional
    ParkingSpot save(ParkingSpot parkingSpot);

    Boolean existsByLicensePlateCar(String licensePlateCar);

    Boolean existsByParkingSpotNumber(String parkingSpotNumber);

    Boolean existsByApartmentAndBlock(String apartmentAndBlock, String block);
}