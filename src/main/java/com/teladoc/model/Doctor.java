package com.teladoc.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Entity(name = "doctor")
@Getter
@Setter
@NoArgsConstructor
@Table(name = "doctor")
public class Doctor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String specialty;
    @OneToMany
    @JoinColumn(name = "doctor_id")
    private List<WorkingHours> workingHours;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id")
    private List<Appointment> appointments;

}

