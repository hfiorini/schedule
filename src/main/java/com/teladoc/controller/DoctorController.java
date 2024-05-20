package com.teladoc.controller;

import com.teladoc.model.AvailableSlot;
import com.teladoc.model.Doctor;

import com.teladoc.model.WorkingHoursView;
import com.teladoc.model.exception.DoctorNotFoundException;
import com.teladoc.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;



    @GetMapping("/{doctorId}/hours")
    public ResponseEntity<?> getDoctorWorkingHours(@PathVariable Long doctorId) {
        try {

            Doctor doctor = doctorService.getDoctorById(doctorId);
            if (doctor == null) {
                return ResponseEntity.notFound().build();
            }
            List<WorkingHoursView> body = doctor.getWorkingHours().stream().map(item ->
                    new WorkingHoursView(item.getId(), item.getDayOfWeek(), item.getStartTime(), item.getEndTime())).collect(Collectors.toCollection(ArrayList::new));
            return ResponseEntity.ok().body(body);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving doctor working hours.");
        }
    }

    @GetMapping("/{doctorId}/availability")
    public ResponseEntity<?> getDoctorAvailability(@PathVariable Long doctorId) {
        try {

            List<AvailableSlot> availability = doctorService.getDoctorAvailability(doctorId);
            return new ResponseEntity<>(availability, HttpStatus.OK);
        } catch (DoctorNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}