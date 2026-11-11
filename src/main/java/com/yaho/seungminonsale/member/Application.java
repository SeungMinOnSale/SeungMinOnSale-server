package com.yaho.seungminonsale.member;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Job job;

    @Column(nullable = false)
    private Worker worker;

    C
    private ApplicationStatus status;

    private LocalDateTime appliedAt;
}
