package gr.xe.rating.service.configurations;

import gr.xe.rating.service.properties.EventsTaskExecutorProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configuration for Event task executor
 */
@Slf4j
@Configuration
@EnableAsync
public class AppAsyncConfiguration {

    private final EventsTaskExecutorProperties eventsTaskExecutorProperties;

    @Autowired
    public AppAsyncConfiguration(EventsTaskExecutorProperties eventsTaskExecutorProperties) {
        this.eventsTaskExecutorProperties = eventsTaskExecutorProperties;
        if (log.isDebugEnabled()) log.debug("Configuration initialized");
    }

    @Bean(name = "AuditEventTaskExecutor")
    public Executor auditEventTaskExecutor() {
        if (log.isInfoEnabled()) log.info("Configuration for auditEventTaskExecutor initializing with values [corePoolSize=" +
                eventsTaskExecutorProperties.getCorePoolSize() + "],[maxPoolSize=" + eventsTaskExecutorProperties.getMaxPoolSize() +
                "],[queueCapacity=" + eventsTaskExecutorProperties.getQueueCapacity() + "]");

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(eventsTaskExecutorProperties.getCorePoolSize());
        executor.setMaxPoolSize(eventsTaskExecutorProperties.getMaxPoolSize());
        executor.setQueueCapacity(eventsTaskExecutorProperties.getQueueCapacity());
        executor.setThreadNamePrefix("auditEvent-");
        executor.initialize();
        return executor;
    }
}