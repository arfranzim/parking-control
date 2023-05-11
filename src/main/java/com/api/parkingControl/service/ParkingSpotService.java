package com.api.parkingControl.service;

import com.api.parkingControl.dto.ParkingSpotDTO;
import com.api.parkingControl.model.ParkingSpot;
import com.api.parkingControl.repository.ParkingSpotRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpotService {
    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Transactional
    public ParkingSpot save(ParkingSpot build){
        return parkingSpotRepository.save(build);
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
    }

    public Page<ParkingSpot> findAll(Pageable pageable) {
        return parkingSpotRepository.findAll(pageable);
    }

    public ResponseEntity<Object> findById(String id) {
        Optional<ParkingSpot> parkingSpotOptional = parkingSpotRepository.findById(id);
        if (!parkingSpotOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found.");
        }
        ParkingSpotDTO dto = ParkingSpotDTO.builder().build();
        BeanUtils.copyProperties(parkingSpotOptional.get(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Transactional
    public ResponseEntity<Object> delete(String id) {
        Optional<ParkingSpot> parkingSpotOptional = parkingSpotRepository.findById(id);
        if (!parkingSpotOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found.");
        }
        parkingSpotRepository.delete(parkingSpotOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking spot delete successfully.");
    }

    public ResponseEntity<Object> update(String id, @Valid ParkingSpotDTO parkingSpotDTO) {
        Optional<ParkingSpot> parkingSpotOptional = parkingSpotRepository.findById(id);
        if (!parkingSpotOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found.");
        }
        String[] ignored = {"_id", "registrationDate"};
        BeanUtils.copyProperties(parkingSpotDTO, parkingSpotOptional.get(), ignored);
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotRepository.save(parkingSpotOptional.get()));
    }
}