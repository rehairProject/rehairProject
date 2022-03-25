package com.rehair.rehair.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Schedule {

	@Id
	private String scheduleDay;
	
	@Enumerated(EnumType.STRING)
	private HolidayStatus status;
	
	@OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
	private List<Reservation> reservations = new ArrayList<>();
}
