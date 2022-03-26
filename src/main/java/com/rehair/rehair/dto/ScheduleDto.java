package com.rehair.rehair.dto;

import com.rehair.rehair.domain.HolidayStatus;
import com.rehair.rehair.domain.Reservation;
import com.rehair.rehair.domain.Schedule;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScheduleDto {

    private String scheduleDay;
    private HolidayStatus status;
    private List<Reservation> reservations = new ArrayList<>();

    public ScheduleDto(Schedule schedule){
        this.scheduleDay = schedule.getScheduleDay();
        this.status = schedule.getStatus();
        this.reservations = schedule.getReservations();
    }

    public Schedule toSchedule(){
        Schedule schedule = new Schedule();
        schedule.setScheduleDay(scheduleDay);
        schedule.setStatus(status);
        schedule.setReservations(reservations);
        return schedule;
    }
}
