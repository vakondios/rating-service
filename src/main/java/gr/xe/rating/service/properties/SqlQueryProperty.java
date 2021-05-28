package gr.xe.rating.service.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "database.sql.rating")
@Component
@Slf4j
public class SqlQueryProperty {

    private String overall;
    private String delete;

    public SqlQueryProperty() {
        if (log.isDebugEnabled()) log.debug("Component initialized");
    }
}
