package com.teladoc.repository;


import com.teladoc.model.Doctor;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Hidden
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}

