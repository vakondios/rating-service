package gr.xe.rating.service.caches;

import com.github.benmanes.caffeine.cache.Caffeine;
import gr.xe.rating.service.properties.CaffeineProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import java.util.concurrent.TimeUnit;

/**
 * Configuration for the Caffeine Cache.
 * Declares the Caches on cache Manager.
 */
@Configuration
@EnableCaching
@Slf4j
public class CaffeineCacheConfig  {

    private final CaffeineProperties caffeineProperties;

    @Autowired
    public CaffeineCacheConfig(CaffeineProperties caffeineProperties){
        this.caffeineProperties = caffeineProperties;
    }

    @Bean
    @Primary
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("audit", "rating");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(caffeineProperties.getInitialCapacity())
                .maximumSize(caffeineProperties.getMaximumSize())
                .expireAfterWrite(caffeineProperties.getExpireInSeconds(), TimeUnit.SECONDS)
                .recordStats());
        return cacheManager;
    }
}