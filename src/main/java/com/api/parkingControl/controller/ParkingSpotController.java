package com.api.parkingControl.controller;

import com.api.parkingControl.dto.ParkingSpotDTO;
import com.api.parkingControl.model.ParkingSpot;
import com.api.parkingControl.service.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping()
    ResponseEntity<Object> save(@RequestBody @Valid ParkingSpotDTO parkingSpotDTO) {
        if(parkingSpotService.existsByLicensePlateCar(parkingSpotDTO.getLicensePlateCar())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
        }
        if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDTO.getParkingSpotNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
        }
        if(parkingSpotService.existsByApartmentAndBlock(parkingSpotDTO.getApartment(), parkingSpotDTO.getBlock())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
        }
        ParkingSpot build = ParkingSpot.builder().build();
        BeanUtils.copyProperties(parkingSpotDTO, build);
        build.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(build));
    }

    @GetMapping
    ResponseEntity<Page<ParkingSpot>> getAllParkingSpots(
            @PageableDefault(page = 0, size = 3, sort = "_id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getByIdParkingSpot(@PathVariable(value = "id") String id) {
        return parkingSpotService.findById(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") String id) {
        return parkingSpotService.delete(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") String id,
                                             @RequestBody @Valid ParkingSpotDTO parkingSpotDTO) {
        return parkingSpotService.update(id, parkingSpotDTO);
    }
}
