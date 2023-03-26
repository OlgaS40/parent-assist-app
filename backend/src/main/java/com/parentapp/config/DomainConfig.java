package com.parentapp.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.parentapp")
@EnableJpaRepositories("com.parentapp")
@EnableTransactionManagement
public class DomainConfig {
}
