package com.rehair.rehair.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Auth {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String authname;

    @ManyToMany(mappedBy = "auth")
    private List<User> users;
}
