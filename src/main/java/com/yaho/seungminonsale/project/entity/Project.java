
package com.yaho.seungminonsale.project.entity;

import com.yaho.seungminonsale.common.BaseTimeEntity;
import com.yaho.seungminonsale.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Setter
    private String name;

    @Setter
    private String description;

    @Setter
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
}
