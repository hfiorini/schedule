package com.teladoc.service;


import com.teladoc.model.AvailableSlot;
import com.teladoc.model.Doctor;
import com.teladoc.model.exception.DoctorNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorService {

    Doctor getDoctorById(Long doctorId) throws DoctorNotFoundException;

    List<AvailableSlot> getDoctorAvailability(Long doctorId) throws DoctorNotFoundException;


}
