package com.yaho.seungminonsale.worker.entity;

import com.yaho.seungminonsale.common.BaseTimeEntity;
import com.yaho.seungminonsale.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "workers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Worker extends BaseTimeEntity {

    public static final int NAME_MAX_LENGTH = 50;
    public static final int PHONE_MAX_LENGTH = 20;
    public static final int SKILLS_MAX_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, length = NAME_MAX_LENGTH)
    private String name;

    @Column(length = PHONE_MAX_LENGTH)
    private String phone;

    @Column(length = SKILLS_MAX_LENGTH)
    private String skills;

    @Column(nullable = false)
    private boolean available;

    private Worker(User user, String name, String phone, String skills) {
        this.user = Objects.requireNonNull(user, "user");
        this.name = Objects.requireNonNull(name, "name");
        this.phone = phone;
        this.skills = skills;
        this.available = true;
    }

    public static Worker create(User user, String name, String phone, String skills) {
        return new Worker(user, name, phone, skills);
    }

    public void update(String name, String phone, String skills, boolean available) {
        this.name = Objects.requireNonNull(name, "name");
        this.phone = phone;
        this.skills = skills;
        this.available = available;
    }
}