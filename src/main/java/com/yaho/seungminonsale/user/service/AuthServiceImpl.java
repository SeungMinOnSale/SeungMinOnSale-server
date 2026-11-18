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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final WorkerRepository workerRepository;

    @Override
    @Transactional
    public SignupResponse signup(SignupRequest request) {
        validateSignupRequest(request);

        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        User user = userRepository.save(User.builder()
                .email(request.email())
                .password(request.password())
                .role(Role.WORKER)
                .build());

        Worker worker = workerRepository.save(Worker.builder()
                .user(user)
                .name(request.name())
                .phone(request.phone())
                .skills(request.skills())
                .build());

        return new SignupResponse(user.getId(), worker.getId(), user.getEmail());
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        if (!StringUtils.hasText(request.email()) || !StringUtils.hasText(request.password())) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, "이메일과 비밀번호를 입력해야 합니다.");
        }

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException(ErrorCode.LOGIN_FAILED));

        if (!user.getPassword().equals(request.password())) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        }
        if (!user.isActive()) {
            throw new BusinessException(ErrorCode.INACTIVE_USER);
        }

        return LoginResponse.from(user);
    }

    private void validateSignupRequest(SignupRequest request) {
        if (!StringUtils.hasText(request.email()) || !request.email().contains("@")) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, "이메일 형식이 올바르지 않습니다.");
        }
        if (request.password() == null || request.password().length() < 8 || request.password().length() > 20) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, "비밀번호는 8자 이상 20자 이하로 입력해야 합니다.");
        }
        if (!StringUtils.hasText(request.name())) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, "이름은 필수입니다.");
        }
    }
}
