package gr.xe.rating.service.caches;

import gr.xe.rating.service.models.dto.OverallRatingDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * Rating Cache. Stores for a specific time, the db results.
 * So the system does not go to db on every call.
 */
@Slf4j
@Component
public class RatingResultsCache extends Cache {

    @Autowired
    public RatingResultsCache(CacheManager cacheManager) {
        super("rating", cacheManager);
        if (log.isDebugEnabled()) log.debug("Component Initialized.");
    }

    public OverallRatingDto getRateResults(String transaction_key) {
        if (transaction_key == null) return null;
        org.springframework.cache.Cache.ValueWrapper obj = readCache(transaction_key);
        if (obj != null ) return (OverallRatingDto) obj.get();

        return null;
    }

    public void newRateResults(OverallRatingDto overallRatingDto) {
        String transaction_key = overallRatingDto.getRatedEntity();
        if (transaction_key != null)  newCache(transaction_key, overallRatingDto);
    }
}

