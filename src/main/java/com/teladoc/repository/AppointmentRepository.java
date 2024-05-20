package com.teladoc.repository;

import com.teladoc.model.Appointment;
import com.teladoc.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorId(Long doctorId);

    Optional<Appointment> findByIdAndDoctorId(Long doctorId, Long appointmentId);
    List<Appointment> findByDoctorAndStartTimeBetween(Doctor doctor, LocalDateTime startDate, LocalDateTime endDate);
}
