package com.devpaik.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ExecutorConfig {
    @Bean("sendCreditCardExecutor")
    public Executor sendCreditCardExecutor() {
        int core = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(core);
        executor.setMaxPoolSize(core * (1 + 50 / 5));
        executor.setThreadNamePrefix("send-credit-card-executor");
        executor.initialize();
        return executor;
    }
}
