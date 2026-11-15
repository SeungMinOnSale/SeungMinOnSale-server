package com.yaho.seungminonsale.application.entity;

import com.yaho.seungminonsale.common.BaseTimeEntity;
import com.yaho.seungminonsale.recruitment.entity.RecruitmentPost;
import com.yaho.seungminonsale.worker.entity.Worker;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "applications")
public class Applications extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_post_id", nullable = false)
    private RecruitmentPost recruitmentPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(nullable = false)
    private String motivation;

    @Column(nullable = false)
    private ApplicationStatus status;
}
