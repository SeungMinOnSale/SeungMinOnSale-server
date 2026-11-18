package com.yaho.seungminonsale.worker.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class WorkerPageRequest {

    private String skills;

    private Boolean available;

    @Min(value = 1, message = "페이지 번호는 {value} 이상이어야 합니다.")
    private int page = 1;

    @Min(value = 1, message = "한 페이지당 학생 수는 {value}명 이상이어야 합니다.")
    @Max(value = 100, message = "한 페이지당 학생 수는 최대 {value}명까지 조회할 수 있습니다.")
    private int size = 10;

    public Pageable toPageable() {
        return PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "id"));
    }
}
