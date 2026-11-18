package com.yaho.seungminonsale.worker.service;

import com.yaho.seungminonsale.common.exception.BusinessException;
import com.yaho.seungminonsale.common.exception.ErrorCode;
import com.yaho.seungminonsale.user.entity.Role;
import com.yaho.seungminonsale.user.entity.User;
import com.yaho.seungminonsale.worker.dto.WorkerResponse;
import com.yaho.seungminonsale.worker.dto.WorkerUpdateRequest;
import com.yaho.seungminonsale.worker.entity.Worker;
import com.yaho.seungminonsale.worker.repository.WorkerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class WorkerServiceImplTest {

    @Mock
    private WorkerRepository workerRepository;

    @InjectMocks
    private WorkerServiceImpl workerService;

    private Worker worker;

    @BeforeEach
    void setUp() {
        User user = User.builder().email("student@test.com").password("password123").role(Role.WORKER).build();
        worker = Worker.builder().user(user).name("김학생").skills("Java").build();
        ReflectionTestUtils.setField(worker, "id", 10L);
    }

    @Test
    @DisplayName("학생 프로필을 요청한 값으로 수정한다")
    void updateWorker() {
        given(workerRepository.findById(10L)).willReturn(Optional.of(worker));

        WorkerResponse response = workerService.updateWorker(10L,
                new WorkerUpdateRequest("김수정", "010-0000-0000", "Spring", false));

        assertThat(response.name()).isEqualTo("김수정");
        assertThat(response.skills()).isEqualTo("Spring");
        assertThat(response.available()).isFalse();
    }

    @Test
    @DisplayName("이름이 없으면 INVALID_INPUT 예외가 발생한다")
    void updateWorkerWithoutName() {
        assertThatThrownBy(() -> workerService.updateWorker(10L,
                new WorkerUpdateRequest(" ", null, null, true)))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.INVALID_INPUT);
    }

    @Test
    @DisplayName("존재하지 않는 학생을 조회하면 WORKER_NOT_FOUND 예외가 발생한다")
    void getWorkerNotFound() {
        given(workerRepository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> workerService.getWorker(99L))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.WORKER_NOT_FOUND);
    }
}
