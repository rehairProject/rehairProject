package com.rehair.rehair.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User {

    @Id @GeneratedValue
    private Long id;
    @Column(name = "username")
    private String username;
    private String password;
    private String name;
    private String phoneNumber;
    private String birth;
    private String gender;
    private Grade grade;
    private LocalDate joinDate;
    private Boolean enabled;

}
