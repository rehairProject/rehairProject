package com.rehair.rehair.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
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
