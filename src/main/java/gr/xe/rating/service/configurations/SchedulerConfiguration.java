package gr.xe.rating.service.configurations;

import gr.xe.rating.service.properties.SchedulerProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * Scheduler Configuration
 */
@Slf4j
@Configuration
public class SchedulerConfiguration implements SchedulingConfigurer {

    private final SchedulerProperty schedulerProperty;

    @Autowired
    public SchedulerConfiguration(SchedulerProperty schedulerProperty) {

        this.schedulerProperty = schedulerProperty;
        if (log.isDebugEnabled()) log.debug("Component Initialized.");
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(schedulerProperty.getPoolSize());
        threadPoolTaskScheduler.setThreadNamePrefix("scheduler-task-pool");
        threadPoolTaskScheduler.initialize();

        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }
}
