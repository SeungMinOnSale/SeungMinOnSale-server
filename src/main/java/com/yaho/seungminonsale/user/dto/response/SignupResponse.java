package com.yaho.seungminonsale.user.dto.response;

import com.yaho.seungminonsale.user.entity.User;
import com.yaho.seungminonsale.worker.entity.Worker;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupResponse {
    private Long userId;
    private Long workerId;
    private String email;

    public static SignupResponse of(User user, Worker worker) {
        return SignupResponse.builder()
                .userId(user.getId())
                .workerId(worker.getId())
                .email(user.getEmail())
                .build();
    }
}
