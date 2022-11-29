package com.gaethering.modulemember.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.gaethering.moduledomain.repository")
@EnableJpaAuditing
public class JpaConfig {

}
