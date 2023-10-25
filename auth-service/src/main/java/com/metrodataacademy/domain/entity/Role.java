package com.metrodataacademy.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity @Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_m_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "tb_user_role", 
        joinColumns = @JoinColumn(name = "role_id"), 
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> user;
}
