package com.yaho.seungminonsale.worker.repository;

import com.yaho.seungminonsale.worker.entity.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Optional<Worker> findByUser_Id(Long userId);

    Page<Worker> findBySkillsContainingIgnoreCase(String skills, Pageable pageable);

    Page<Worker> findByAvailable(boolean available, Pageable pageable);

    Page<Worker> findBySkillsContainingIgnoreCaseAndAvailable(String skills, boolean available, Pageable pageable);
}
