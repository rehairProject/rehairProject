package com.rehair.rehair.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String designer;
    private String surgery;
    private int price;

    @Column(columnDefinition = "boolean default true")
    private boolean isEnabled; //예약상태  예약or취소
    private LocalDateTime reservationTime; //예약시간
    @CreatedDate
    private LocalDateTime createdDate; // 예약이 생성된 시간

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="schedule_id")
    private Schedule schedule;
}
