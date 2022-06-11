package com.rehair.rehair.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(unique = true, length = 50, nullable = false)
    private String username;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 50)
    private String phoneNumber;
//    @Column(nullable = false, length = 50)
    @Column(length = 50)
    private String birth;
    @Column(nullable = false, length = 5)
    private String gender;
    @Column
    @Enumerated(EnumType.STRING)
    private Grade grade;
    @Column(nullable = false)
    private LocalDate joinDate;
    @Column(columnDefinition = "boolean default true")
    private boolean isEnabled;

    @CreatedDate
    private LocalDateTime dateCreated;

    @ManyToMany
    @JoinTable(
            name = "user_auth",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="auth_id")
    )
    private List<Auth> auths = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();
}