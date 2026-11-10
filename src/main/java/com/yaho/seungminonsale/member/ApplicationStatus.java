package com.yaho.seungminonsale.member;

import jakarta.persistence.ManyToOne;

public class ApplicationStatus {

    private Long id;
    private Job job;

    @ManyToOne
    private Worker worker;
}
