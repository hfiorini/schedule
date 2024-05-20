package com.teladoc.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DoctorControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void getWorkingHoursWith200() throws Exception {
        mvc.perform(get("/doctors/{doctorId}/hours", 123)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

    }

    @Test
    public void getWorkingHoursExpectsError500() throws Exception {
        mvc.perform(get("/doctors/{doctorId}/hours", 1230)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is5xxServerError());

    }

    @Test
    public void getWorkingHoursForInvalidIDExpectsError500() throws Exception {
        mvc.perform(get("/doctors/{doctorId}/hours", "NO_ID")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void getAvailabilityWith200() throws Exception {
        mvc.perform(get("/doctors/{doctorId}/availability", 123)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

    }

    @Test
    public void getAvailabilityWithInvalidDoctorExpectsError400() throws Exception {
        mvc.perform(get("/doctors/{doctorId}/availability", 1230)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void getAvailabilityWithInvalidDoctorIDExpectsError400() throws Exception {
        mvc.perform(get("/doctors/{doctorId}/availability", "INVALID_ID")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError());

    }
}
