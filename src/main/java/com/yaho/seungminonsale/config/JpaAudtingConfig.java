package com.yaho.seungminonsale.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
class JpaAudtingConfig {


    // 등록일 수정일 등록자 수정자
//    @Bean
//    public AuditorAware<AuditableUser> auditorProvider() {
//        return new AuditorAwareImpl();
//    }
}
