package com.yaho.seungminonsale.member;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "companys")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user-id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String name;

    private String phone;

    @Column(nullable = false)
    private LocalDateTime createdAt;


}
