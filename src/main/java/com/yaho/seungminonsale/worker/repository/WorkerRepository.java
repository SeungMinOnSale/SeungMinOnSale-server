package com.yaho.seungminonsale.worker.repository;

import com.yaho.seungminonsale.worker.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}