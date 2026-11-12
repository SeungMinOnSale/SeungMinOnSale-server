package com.yaho.seungminonsale.user.service;

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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WorkerRepository workerRepository;

    @Override
    public SignupResponse signup(SignupRequest request) {
        validateSignupRequest(request);

        if (userRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다.");
        }

        User user = userRepository.save(User.builder()
                .email(request.email())
                .passwordHash(request.password())
                .role(request.role())
                .build());

        Long workerId = null;
        if (request.role() == Role.WORKER) {
            Worker worker = workerRepository.save(Worker.builder()
                    .user(user)
                    .name(request.name())
                    .phone(request.phone())
                    .skills(request.skills())
                    .build());
            workerId = worker.getId();
        }

        return new SignupResponse(user.getId(), user.getEmail(), user.getRole(), workerId);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        if (!StringUtils.hasText(request.email()) || !StringUtils.hasText(request.password())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일과 비밀번호를 입력해야 합니다.");
        }

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!user.getPasswordHash().equals(request.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다.");
        }
        if (!user.isActive()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비활성화된 회원입니다.");
        }

        return LoginResponse.from(user);
    }

    private void validateSignupRequest(SignupRequest request) {
        if (!StringUtils.hasText(request.email()) || !request.email().contains("@")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일 형식이 올바르지 않습니다.");
        }
        if (request.password() == null || request.password().length() < 8 || request.password().length() > 20) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 8자 이상 20자 이하로 입력해야 합니다.");
        }
        if (request.role() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "회원 유형은 필수입니다.");
        }
        if (request.role() == Role.WORKER
                && (!StringUtils.hasText(request.name()) || !StringUtils.hasText(request.phone()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "근로자 회원은 이름과 연락처를 입력해야 합니다.");
        }
    }
}