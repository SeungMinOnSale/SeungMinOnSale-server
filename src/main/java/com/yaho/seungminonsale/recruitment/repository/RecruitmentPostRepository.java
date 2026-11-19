package com.yaho.seungminonsale.recruitment.repository;

import com.yaho.seungminonsale.recruitment.entity.RecruitmentPost;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecruitmentPostRepository extends JpaRepository<RecruitmentPost, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        select r
        from RecruitmentPost r
        where r.id = :id
    """)
    Optional<RecruitmentPost> findWithLockById(@Param("id") Long id);
}
