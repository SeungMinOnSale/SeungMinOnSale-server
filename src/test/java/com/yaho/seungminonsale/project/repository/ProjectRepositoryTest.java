
package com.yaho.seungminonsale.project.repository;

import com.yaho.seungminonsale.project.entity.Project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void findAllByOwnerIdTest() {

        List<Project> projects =
                projectRepository.findAllByOwner_Id(-1L);

        assertThat(projects).isEmpty();
    }
}
