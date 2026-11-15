package com.yaho.seungminonsale.project.entity;


import com.yaho.seungminonsale.common.BaseTimeEntity;
import com.yaho.seungminonsale.user.entity.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    private String name;

    private String description;

    private ProjectStatus status;
}
