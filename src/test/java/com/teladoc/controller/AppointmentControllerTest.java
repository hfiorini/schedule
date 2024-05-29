package com.teladoc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.teladoc.controller.request.AppointmentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppointmentControllerTest {
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
    public void createAppointmentHappyPathExpectsStatus200() throws Exception {
        AppointmentRequest request = new AppointmentRequest(123L, 1L, LocalDateTime.now());
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        mvc.perform(post("/appointments/appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8))
                )
                .andExpect(status().isCreated());

    }

    @Test
    public void createAppointmentInvalidDoctorIdExpectsStatus500() throws Exception {
        AppointmentRequest request = new AppointmentRequest(1230L, 1L, LocalDateTime.now());
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        mvc.perform(post("/appointments/appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8))
                )
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void getWorkingHoursForInvalidIDExpectsError500() throws Exception {
        AppointmentRequest request = new AppointmentRequest(123L, 1L, LocalDateTime.now());
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        mvc.perform(post("/appointments/appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8))
                )
                .andExpect(status().isCreated());

    }

}
