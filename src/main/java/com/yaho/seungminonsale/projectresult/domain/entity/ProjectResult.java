package com.yaho.seungminonsale.projectresult.domain.entity;

import com.yaho.seungminonsale.common.BaseTimeEntity;
import com.yaho.seungminonsale.project.entity.Project;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "project_results")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectResult extends BaseTimeEntity {

    public static final int SUMMARY_MAX_LENGTH = 255;
    public static final int REPOSITORY_URL_MAX_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_project_id", nullable = false, unique = true)
    private Project project;

    @Column(nullable = false, length = SUMMARY_MAX_LENGTH)
    private String summary;

    @Column(length = REPOSITORY_URL_MAX_LENGTH)
    private String repositoryUrl;

    private ProjectResult(Project project, String summary, String repositoryUrl) {
        this.project = Objects.requireNonNull(project, "project");
        this.summary = Objects.requireNonNull(summary, "summary");
        this.repositoryUrl = repositoryUrl;
    }

    public static ProjectResult create(Project project, String summary, String repositoryUrl) {
        return new ProjectResult(project, summary, repositoryUrl);
    }
}
