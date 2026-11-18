package com.yaho.seungminonsale.recruitment.entity;


import com.yaho.seungminonsale.common.BaseTimeEntity;
import com.yaho.seungminonsale.project.entity.Project;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name = "recruitment_posts")
@Getter
public class RecruitmentPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false)
    private String title;

    private String position;

    @Column(nullable = false)
    private String requiredSkills;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private RecruitmentStatus status;
}
