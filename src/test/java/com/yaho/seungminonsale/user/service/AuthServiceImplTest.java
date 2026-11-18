package com.yaho.seungminonsale.user.service;

import com.yaho.seungminonsale.common.exception.BusinessException;
import com.yaho.seungminonsale.common.exception.ErrorCode;
import com.yaho.seungminonsale.user.dto.LoginRequest;
import com.yaho.seungminonsale.user.dto.LoginResponse;
import com.yaho.seungminonsale.user.dto.SignupRequest;
import com.yaho.seungminonsale.user.dto.SignupResponse;
import com.yaho.seungminonsale.user.entity.Role;
import com.yaho.seungminonsale.user.entity.User;
import com.yaho.seungminonsale.user.repository.UserRepository;
import com.yaho.seungminonsale.worker.entity.Worker;
import com.yaho.seungminonsale.worker.repository.WorkerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WorkerRepository workerRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    @DisplayName("회원가입 시 WORKER 권한으로 User를 저장하고 학생 프로필을 함께 생성한다")
    void signup() {
        SignupRequest request = new SignupRequest("student@test.com", "password123", "김학생", null, "Java");
        given(userRepository.existsByEmail(request.email())).willReturn(false);
        given(userRepository.save(any(User.class))).willAnswer(invocation -> {
            User user = invocation.getArgument(0);
            ReflectionTestUtils.setField(user, "id", 1L);
            return user;
        });
        given(workerRepository.save(any(Worker.class))).willAnswer(invocation -> {
            Worker worker = invocation.getArgument(0);
            ReflectionTestUtils.setField(worker, "id", 10L);
            return worker;
        });

        SignupResponse response = authService.signup(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertThat(userCaptor.getValue().getRole()).isEqualTo(Role.WORKER);
        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.workerId()).isEqualTo(10L);
    }

    @Test
    @DisplayName("이미 가입된 이메일이면 DUPLICATE_EMAIL 예외가 발생한다")
    void signupDuplicateEmail() {
        SignupRequest request = new SignupRequest("dup@test.com", "password123", "김학생", null, null);
        given(userRepository.existsByEmail(request.email())).willReturn(true);

        assertThatThrownBy(() -> authService.signup(request))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.DUPLICATE_EMAIL);
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("이름이 없으면 INVALID_INPUT 예외가 발생한다")
    void signupWithoutName() {
        SignupRequest request = new SignupRequest("student@test.com", "password123", " ", null, null);

        assertThatThrownBy(() -> authService.signup(request))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.INVALID_INPUT);
    }

    @Test
    @DisplayName("로그인에 성공하면 회원 정보를 반환한다")
    void loginSuccess() {
        User user = User.builder().email("student@test.com").password("password123").role(Role.WORKER).build();
        ReflectionTestUtils.setField(user, "id", 1L);
        given(userRepository.findByEmail("student@test.com")).willReturn(Optional.of(user));

        LoginResponse response = authService.login(new LoginRequest("student@test.com", "password123"));

        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.role()).isEqualTo(Role.WORKER);
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 LOGIN_FAILED 예외가 발생한다")
    void loginWrongPassword() {
        User user = User.builder().email("student@test.com").password("password123").role(Role.WORKER).build();
        given(userRepository.findByEmail("student@test.com")).willReturn(Optional.of(user));

        assertThatThrownBy(() -> authService.login(new LoginRequest("student@test.com", "wrong-password")))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.LOGIN_FAILED);
    }

    @Test
    @DisplayName("비활성 계정이면 INACTIVE_USER 예외가 발생한다")
    void loginInactiveUser() {
        User user = User.builder().email("student@test.com").password("password123").role(Role.WORKER).build();
        ReflectionTestUtils.setField(user, "active", false);
        given(userRepository.findByEmail("student@test.com")).willReturn(Optional.of(user));

        assertThatThrownBy(() -> authService.login(new LoginRequest("student@test.com", "password123")))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.INACTIVE_USER);
    }
}
