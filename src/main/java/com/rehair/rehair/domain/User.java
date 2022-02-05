package com.rehair.rehair.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 50, nullable = false)
    private String username;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 50)
    private String phoneNumber;
    @Column(nullable = false, length = 50)
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

    @ManyToMany
    @JoinTable(
            name = "user_auth",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="auth_id")
    )
    private List<Auth> auths = new ArrayList<>();

}
