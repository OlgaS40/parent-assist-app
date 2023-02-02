package com.parentapp.parent_assist.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.parentapp.parent_assist")
@EnableJpaRepositories("com.parentapp.parent_assist")
@EnableTransactionManagement
public class DomainConfig {
}
