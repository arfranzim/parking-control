package com.api.parkingControl.controller;

import com.api.parkingControl.util.BusinessValidation;
import com.api.parkingControl.dto.ParkingSpotDTO;
import com.api.parkingControl.model.ParkingSpot;
import com.api.parkingControl.service.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;
    public static final String NOT_FOUND = "Parking spot not found.";
    public static final String SUCCESSFULLY_DELETED = "Parking spot delete successfully.";

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping()
    ResponseEntity<Object> save(@RequestBody @Valid ParkingSpotDTO parkingSpotDTO) {
        String CONFLICT = BusinessValidation.spotValidate(parkingSpotDTO);
        if (Objects.nonNull(CONFLICT)) return ResponseEntity.status(HttpStatus.CONFLICT).body(CONFLICT);
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotDTO));
    }

    @GetMapping
    ResponseEntity<Page<ParkingSpot>> getAllParkingSpots( @PageableDefault(page = 0, size = 3, sort = "_id",
                                                          direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getByIdParkingSpot(@PathVariable(value = "id") String id) {
        ParkingSpotDTO parkingSpotDTO = parkingSpotService.findById(id);
        if(Objects.isNull(parkingSpotDTO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotDTO);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") String id) {
        if (Objects.isNull(parkingSpotService.delete(id))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(SUCCESSFULLY_DELETED);
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") String id,
                                             @RequestBody @Valid ParkingSpotDTO parkingSpotDTO) {
        ParkingSpotDTO updated = parkingSpotService.update(id, parkingSpotDTO);
        if(Objects.isNull(updated)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
}