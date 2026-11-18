package com.yaho.seungminonsale.user.service;

import com.yaho.seungminonsale.exception.DuplicateEmailException;
import com.yaho.seungminonsale.exception.InactiveUserException;
import com.yaho.seungminonsale.exception.LoginFailedException;
import com.yaho.seungminonsale.user.dto.request.LoginRequest;
import com.yaho.seungminonsale.user.dto.request.SignupRequest;
import com.yaho.seungminonsale.user.dto.response.LoginResponse;
import com.yaho.seungminonsale.user.dto.response.SignupResponse;
import com.yaho.seungminonsale.user.entity.User;
import com.yaho.seungminonsale.user.repository.UserRepository;
import com.yaho.seungminonsale.worker.entity.Worker;
import com.yaho.seungminonsale.worker.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final WorkerRepository workerRepository;

    @Override
    @Transactional
    public SignupResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException();
        }

        User user = userRepository.save(request.toUser());
        Worker worker = workerRepository.save(request.toWorker(user));
        return SignupResponse.of(user, worker);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(LoginFailedException::new);

        if (!user.getPassword().equals(request.getPassword())) {
            throw new LoginFailedException();
        }
        if (!user.isActive()) {
            throw new InactiveUserException();
        }
        return LoginResponse.from(user);
    }
}
