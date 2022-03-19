package com.rehair.rehair.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Schedule {
    @Id @GeneratedValue
    private String reservationTime;

    private String designer;

    @OneToMany(mappedBy = "schedule")
    private List<Reservation> reservations=new ArrayList<>();

}

