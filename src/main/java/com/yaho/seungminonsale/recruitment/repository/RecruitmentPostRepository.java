package com.yaho.seungminonsale.recruitment.repository;

import com.yaho.seungminonsale.recruitment.entity.RecruitmentPost;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentPostRepository extends JpaRepository<RecruitmentPost, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = """
        select count(r)
        from RecruitmentPost r
        where r.id = :id and r.status = 'ACCEPTED'
    """)
    Integer countByIdForUpdate(@Param("id") Long id);
}
