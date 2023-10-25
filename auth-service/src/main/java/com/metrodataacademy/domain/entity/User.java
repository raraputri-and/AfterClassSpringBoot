package com.metrodataacademy.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity @Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_m_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, columnDefinition = "varchar(36)", unique=true, updatable = false, nullable = false)
    private String id;

    //RegisteredAt
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;


    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(length = 25, nullable = false, unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private String name;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column
    private String alamat;

    @Column
    private String jenisKelamin;

    @Column
    private String profilePicture;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tanggalLahir;

    @ManyToMany(mappedBy = "user")
    Set<Role> role = new HashSet<>();
}
