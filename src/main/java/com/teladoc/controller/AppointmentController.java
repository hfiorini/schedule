package com.teladoc.controller;

import com.teladoc.model.exception.AppointmentNotFoundException;
import com.teladoc.model.exception.DoctorNotFoundException;
import com.teladoc.model.exception.InvalidAppointmentException;
import com.teladoc.model.request.AppointmentRequest;
import com.teladoc.model.request.UpdateAppointmentRequest;
import com.teladoc.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;


    @PostMapping("/appointment")
    public ResponseEntity<?> bookAppointment(@RequestBody AppointmentRequest request) {
        try {
            Long appointmentId = appointmentService.bookAppointment(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Appointment booked successfully. Appointment ID: " + appointmentId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to book appointment. Please try again later.");
        }
    }

    @PutMapping("/{doctorId}/appointments/{appointmentId}")
    public ResponseEntity<?> updateAppointment(
            @PathVariable String doctorId,
            @PathVariable String appointmentId,
            @RequestBody UpdateAppointmentRequest updateRequest) {
        try {
            Long doctor = Long.valueOf(doctorId);
            Long appointment = Long.valueOf(appointmentId);

            appointmentService.updateAppointment(doctor, appointment, updateRequest);
            return new ResponseEntity<>("Appointment updated successfully", HttpStatus.OK);
        } catch (DoctorNotFoundException | AppointmentNotFoundException | InvalidAppointmentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while updating the appointment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{doctorId}/appointments/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(
            @PathVariable String appointmentId) {
        try {

            Long appointment = Long.valueOf(appointmentId);
            appointmentService.deleteAppointment(appointment);
            return new ResponseEntity<>("Appointment deleted successfully", HttpStatus.OK);
        } catch (AppointmentNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the appointment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
