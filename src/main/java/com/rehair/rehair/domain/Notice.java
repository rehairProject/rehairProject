package com.rehair.rehair.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class Notice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String username;
    @Column(nullable = false, length = 100)
    private String title;
    @Lob // DB에 VARCHAR(255) 보다 큰 문자를 저장하고 싶을 때
    private String content;

    @CreatedDate
    private LocalDateTime dateCreated;
    @LastModifiedDate
    private LocalDateTime dateUpdated;

}

