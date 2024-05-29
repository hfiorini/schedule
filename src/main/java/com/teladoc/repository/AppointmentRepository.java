package com.teladoc.repository;

import com.teladoc.model.Appointment;
import com.teladoc.model.Doctor;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Hidden
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByIdAndDoctorId(Long doctorId, Long appointmentId);

    List<Appointment> findByDoctorAndStartTimeBetween(Doctor doctor, LocalDateTime startDate, LocalDateTime endDate);
}
