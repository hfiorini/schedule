package com.teladoc.service.impl;


import com.teladoc.model.Appointment;
import com.teladoc.model.AvailableSlot;
import com.teladoc.model.Doctor;
import com.teladoc.model.WorkingHours;
import com.teladoc.model.exception.DoctorNotFoundException;
import com.teladoc.repository.AppointmentRepository;
import com.teladoc.repository.DoctorRepository;
import com.teladoc.repository.PatientRepository;
import com.teladoc.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService  {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientRepository patientRepository;

    public Doctor getDoctorById(Long doctorId) throws DoctorNotFoundException{

        return doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
    }

    @Override
    public List<AvailableSlot> getDoctorAvailability(Long doctorId) throws DoctorNotFoundException {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));

        List<Appointment> appointments = getThisWeekAppointments(doctor);
        List<WorkingHours> workingHours = getThisWeekWorkingHours(doctor);

        List<AvailableSlot> appointmentSlots = appointments.stream().map(item -> {
            DayOfWeek dayOfWeek = item.getStartTime().getDayOfWeek();
            return new AvailableSlot(dayOfWeek, item.getStartTime().toLocalTime(), item.getEndTime().toLocalTime());
        }).collect(Collectors.toCollection(ArrayList::new));
        List<AvailableSlot> workingSlots = workingHours.stream().map(item -> new AvailableSlot(item.getDayOfWeek(), item.getStartTime(), item.getEndTime()))
                .collect(Collectors.toCollection(ArrayList::new));

        workingSlots.removeAll(appointmentSlots);

        return workingSlots;
    }

    private List<Appointment> getThisWeekAppointments(Doctor doctor) {
        LocalDateTime startDate = LocalDateTime.now().with(DayOfWeek.MONDAY);
        LocalDateTime endDate = LocalDateTime.now().with(DayOfWeek.SUNDAY);
        return appointmentRepository.findByDoctorAndStartTimeBetween(doctor, startDate, endDate);
    }

    private List<WorkingHours> getThisWeekWorkingHours(Doctor doctor) {
        return doctor.getWorkingHours();
    }

}
