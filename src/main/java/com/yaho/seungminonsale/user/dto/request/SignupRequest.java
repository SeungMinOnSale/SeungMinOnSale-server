package com.yaho.seungminonsale.user.dto.request;

import com.yaho.seungminonsale.user.entity.Role;
import com.yaho.seungminonsale.user.entity.User;
import com.yaho.seungminonsale.worker.entity.Worker;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Size(max = User.EMAIL_MAX_LENGTH, message = "이메일은 {max}자 이하로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = User.PASSWORD_MIN_LENGTH, max = User.PASSWORD_MAX_LENGTH,
            message = "비밀번호는 {min}자 이상 {max}자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(max = Worker.NAME_MAX_LENGTH, message = "이름은 {max}자 이하로 입력해주세요.")
    private String name;

    @Size(max = Worker.PHONE_MAX_LENGTH, message = "연락처는 {max}자 이하로 입력해주세요.")
    private String phone;

    @Size(max = Worker.SKILLS_MAX_LENGTH, message = "기술 스택은 {max}자 이하로 입력해주세요.")
    private String skills;

    public User toUser() {
        return User.create(email, password, Role.WORKER);
    }

    public Worker toWorker(User user) {
        return Worker.create(user, name, phone, skills);
    }
}
