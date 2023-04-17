package com.greedy.data.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/* Application 파일의 패키지 위치가 기본 위치에서 변경 될 경우 Entity, JpaRepository의 Scan 범위를 설정해주어야 한다. */
@Configuration
@EntityScan(basePackages= {"com.greedy.data"})
@EnableJpaRepositories(basePackages={"com.greedy.data"})
public class JPAConfiguration {

}
