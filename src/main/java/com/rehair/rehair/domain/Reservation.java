package com.rehair.rehair.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class Reservation {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reservation_id")
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_user_id")
	private User user;

	@Column(name = "reservation_day")
	private String day;
	@Column(name = "reservation_time")
	private String time;
	@Column(name = "reservation_designer")
	private String designer;
	@Column(name = "reservation_style")
	private String style;
	@Column(name = "reservation_price")
	private int price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_schedule_day")
	private Schedule schedule;
	
	@Enumerated(EnumType.STRING)
	private ReservationStatus status; // 예약 상태
	
    @CreatedDate
    private LocalDateTime dateCreated;
    
    // 연관관계 메소드
    
    public void setUser(User user) {
		this.user = user;
		user.getReservations().add(this);
	}
    
    public void setScheduler(Schedule schedule) {
    	this.schedule = schedule;
    	schedule.getReservations().add(this);
    }
    
    // 가격 할인 로직
    
    public int setPrice(int price) {
    	int calcPrice = 0;
    	if(user.getGrade() == Grade.VIP) {
    		calcPrice = (int)(price * 0.95);
    	}else if(user.getGrade() == Grade.FAMILY) {
    		calcPrice = (int)(price * 0.97);
    	}else
    		calcPrice = price;
     	return calcPrice;
    }
    
}
