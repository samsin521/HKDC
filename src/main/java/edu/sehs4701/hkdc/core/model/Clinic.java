package edu.sehs4701.hkdc.core.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "clinic")
@Getter
@Setter
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String address;

    @Column(length = 100)
    private String openHours;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL)
    private List<DentistSchedule> schedules;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
