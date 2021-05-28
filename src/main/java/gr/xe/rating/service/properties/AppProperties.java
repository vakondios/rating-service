package gr.xe.rating.service.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "info.app")
@Component
@Slf4j
public class AppProperties {
    private String version = "v1.0.0";
    private String name = "RatingDto";
    private String description="Microservice - RatingDto Service";

    public AppProperties() {
        if (log.isDebugEnabled()) log.debug("Component initialized");
    }
}
