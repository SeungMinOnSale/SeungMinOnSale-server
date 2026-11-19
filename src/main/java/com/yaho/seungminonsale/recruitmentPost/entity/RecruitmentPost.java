package com.yaho.seungminonsale.recruitmentPost.entity;


import com.yaho.seungminonsale.common.BaseTimeEntity;
import com.yaho.seungminonsale.project.entity.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Entity
@Table(name = "recruitment_posts")
@Getter
@AllArgsConstructor
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

    public boolean isOpen() {
        return this.status == RecruitmentStatus.OPEN;
    }

    public static RecruitmentPost createDefault(
            @NonNull Project project,
            @NonNull String title,
            @NonNull String position,
            @NonNull String requiredSkills,
            @NonNull Integer capacity
    ) {
        return new RecruitmentPost(
                null,
                project,
                title,
                position,
                requiredSkills,
                capacity,
                RecruitmentStatus.OPEN
        );
    }
}
