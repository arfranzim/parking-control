package com.api.parkingControl.controller;

import com.api.parkingControl.dto.ParkingSpotDTO;
import com.api.parkingControl.repository.ParkingSpotRepository;
import com.api.parkingControl.repository.UserMongoRepository;
import com.api.parkingControl.service.ParkingSpotService;
import com.api.parkingControl.util.BusinessValidation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(ParkingSpotController.class)
public class ParkingSpotControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ParkingSpotService parkingSpotService;
    @MockBean
    private BusinessValidation businessValidation;
    @MockBean
    private ParkingSpotRepository parkingSpotRepository;
    @MockBean
    private UserMongoRepository userMongoRepository;

    @Test
    public void healthCheck() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/parking-spot/api"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void save() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .post("/parking-spot")
                        .content(MOCK_CONTROLLER.getParkingSpotDTO())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getAllParkingSpots() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/parking-spot")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdParkingSpot() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .get("/parking-spot")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("id", "39")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void removeParkingSpot() throws Exception {
        Mockito.when(parkingSpotService.delete(any())).thenReturn("id");
        mvc.perform( MockMvcRequestBuilders
                        .delete("/parking-spot/{id}", "id"))
                        .andExpect(status().isNoContent());
    }

    @Test
    void updateParkingSpot() throws Exception {
        Mockito.when(parkingSpotService.update(any(), any(ParkingSpotDTO.class)))
                                                .thenReturn(ParkingSpotDTO.builder().build());
        mvc.perform( MockMvcRequestBuilders
                        .put("/parking-spot/{id}", "id")
                        .content(MOCK_CONTROLLER.getParkingSpotDTO())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}