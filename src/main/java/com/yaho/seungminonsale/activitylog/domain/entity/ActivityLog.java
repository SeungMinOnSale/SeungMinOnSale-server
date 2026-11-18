package com.yaho.seungminonsale.activitylog.domain.entity;

import com.yaho.seungminonsale.common.BaseTimeEntity;
import com.yaho.seungminonsale.teammembership.domain.entity.TeamMembership;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "activity_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityLog extends BaseTimeEntity {

    public static final int CONTENT_MAX_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_team_membership_id", nullable = false)
    private TeamMembership teamMembership;

    @Column(nullable = false, length = CONTENT_MAX_LENGTH)
    private String content;

    @Column(nullable = false)
    private LocalDate activityDate;

    private ActivityLog(TeamMembership teamMembership, String content, LocalDate activityDate) {
        this.teamMembership = Objects.requireNonNull(teamMembership, "teamMembership");
        this.content = Objects.requireNonNull(content, "content");
        this.activityDate = Objects.requireNonNull(activityDate, "activityDate");
    }

    public static ActivityLog create(TeamMembership teamMembership, String content, LocalDate activityDate) {
        return new ActivityLog(teamMembership, content, activityDate);
    }
}
