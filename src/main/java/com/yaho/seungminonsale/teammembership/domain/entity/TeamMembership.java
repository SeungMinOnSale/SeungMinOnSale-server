package com.yaho.seungminonsale.teammembership.domain.entity;

import com.yaho.seungminonsale.application.entity.Application;
import com.yaho.seungminonsale.common.BaseTimeEntity;
import com.yaho.seungminonsale.project.entity.Project;
import com.yaho.seungminonsale.teammembership.domain.enums.MembershipStatus;
import com.yaho.seungminonsale.worker.entity.Worker;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

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
public class TeamMembership extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_application_id", nullable = false, unique = true)
    private Application application;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_worker_id", nullable = false)
    private Worker worker;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipStatus status;

    private TeamMembership(Application application) {
        this.application = Objects.requireNonNull(application, "application");
        this.project = application.getRecruitmentPost().getProject();
        this.worker = application.getWorker();
        this.status = MembershipStatus.ACTIVE;
    }

    public static TeamMembership create(Application application) {
        return new TeamMembership(application);
    }

    public void complete() {
        this.status = MembershipStatus.COMPLETED;
    }
}
