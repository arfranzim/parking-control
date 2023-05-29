package com.api.parkingControl.service;

import com.api.parkingControl.dto.ParkingSpotDTO;
import com.api.parkingControl.model.ParkingSpot;
import com.api.parkingControl.repository.ParkingSpotRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;
    public static final String UTC = "UTC";
    public static final String ID = "_id";
    public static final String REGISTRATION_DATE = "registrationDate";

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Transactional
    public ParkingSpotDTO save(ParkingSpotDTO parkingSpotDTO) {
        ParkingSpot build = ParkingSpot.builder().build();
        BeanUtils.copyProperties(parkingSpotDTO, build);
        build.setRegistrationDate(LocalDateTime.now(ZoneId.of(UTC)));
        BeanUtils.copyProperties(parkingSpotRepository.save(build), parkingSpotDTO);
        return parkingSpotDTO;
    }

    public Page<ParkingSpot> findAll(Pageable pageable) {
        return parkingSpotRepository.findAll(pageable);
    }

    public ParkingSpotDTO findById(String id) {
        Optional<ParkingSpot> parkingSpotOptional = parkingSpotRepository.findById(id);
        if (parkingSpotOptional.isEmpty()) return null;
        ParkingSpotDTO dto = ParkingSpotDTO.builder().build();
        BeanUtils.copyProperties(parkingSpotOptional.get(), dto);
        return dto;
    }

    @Transactional
    public String delete(String id) {
        Optional<ParkingSpot> parkingSpotOptional = parkingSpotRepository.findById(id);
        if (parkingSpotOptional.isEmpty()) return null;
        parkingSpotRepository.delete(parkingSpotOptional.get());
        return parkingSpotOptional.get().get_id();
    }

    public ParkingSpotDTO update(String id, @Valid ParkingSpotDTO parkingSpotDTO) {
        Optional<ParkingSpot> parkingSpotOptional = parkingSpotRepository.findById(id);
        if (parkingSpotOptional.isEmpty()) return null;
        String[] ignored = {ID, REGISTRATION_DATE};
        BeanUtils.copyProperties(parkingSpotDTO, parkingSpotOptional.get(), ignored);
        BeanUtils.copyProperties(parkingSpotRepository.save(parkingSpotOptional.get()), parkingSpotDTO);
        return parkingSpotDTO;
    }
}