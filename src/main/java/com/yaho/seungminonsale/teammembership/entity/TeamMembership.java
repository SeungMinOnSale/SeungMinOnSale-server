package com.yaho.seungminonsale.teammembership.entity;

import com.yaho.seungminonsale.application.entity.Applications;
import com.yaho.seungminonsale.common.BaseTimeEntity;
import com.yaho.seungminonsale.project.entity.Project;
import com.yaho.seungminonsale.teammembership.enums.MembershipStatus;
import com.yaho.seungminonsale.worker.entity.Worker;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "team_memberships",
        uniqueConstraints= {
                @UniqueConstraint(
                        name = "fk_project_to_worker",
                        columnNames = {
                                "fk_project_id",
                                "fk_worker_id"
                        }
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TeamMembership extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "fk_application_id", nullable = false, unique = true)
    private Applications application;

    @ManyToOne
    @JoinColumn(name = "fk_project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "fk_worker_id", nullable = false)
    private Worker worker;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipStatus status;
}
