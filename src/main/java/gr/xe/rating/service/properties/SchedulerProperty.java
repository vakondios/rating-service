package gr.xe.rating.service.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "scheduler")
@Component
@Slf4j
public class SchedulerProperty {

    private int poolSize = 10;
    private long dbCleanupSchedulerPeriod = 1000*60*60*24;

    public SchedulerProperty() {
        if (log.isDebugEnabled()) log.debug("Component initialized");
    }
}