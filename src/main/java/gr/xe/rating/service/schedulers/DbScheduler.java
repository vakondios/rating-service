package gr.xe.rating.service.schedulers;

import gr.xe.rating.service.properties.SchedulerProperty;
import gr.xe.rating.service.services.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DbScheduler {

    private final SchedulerProperty schedulerProperty;
    private final RatingService ratingService;

    @Autowired
    public DbScheduler(SchedulerProperty schedulerProperty,
                       RatingService ratingService){

        this.schedulerProperty = schedulerProperty;
        this.ratingService = ratingService;

        if (log.isDebugEnabled()) log.debug("Component Initialized.");
    }

    @Scheduled(fixedRate = 2000, initialDelay = 5000)
    public void schedulerDbCleanup() {
        if (log.isInfoEnabled()) log.info("Scheduler started at {}", System.currentTimeMillis());
        ratingService.removeRatesOver100Days();
        if (log.isInfoEnabled()) log.info("Scheduler completed at {}", System.currentTimeMillis());
    }
}
