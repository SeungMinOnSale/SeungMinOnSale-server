package com.yaho.seungminonsale.worker.dto.request;

import com.yaho.seungminonsale.worker.entity.Worker;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WorkerUpdateRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(max = Worker.NAME_MAX_LENGTH, message = "이름은 {max}자 이하로 입력해주세요.")
    private String name;

    @Size(max = Worker.PHONE_MAX_LENGTH, message = "연락처는 {max}자 이하로 입력해주세요.")
    private String phone;

    @Size(max = Worker.SKILLS_MAX_LENGTH, message = "기술 스택은 {max}자 이하로 입력해주세요.")
    private String skills;

    @NotNull(message = "참여 가능 여부를 입력해주세요.")
    private Boolean available;
}
