package com.github.weeniearms.graffiti.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration {

    public static final String GRAPH = "graphCache";

    @Bean(GRAPH)
    public Cache healthCache(@Value("${cache.time-to-live:1800000}") long timeToLive, @Value("${cache.max-weight:104857600}") long maxWeight) {
        return new GuavaCache(GRAPH, CacheBuilder.newBuilder()
                .expireAfterWrite(timeToLive, TimeUnit.MILLISECONDS)
                .weigher((key, value) -> ((byte[]) value).length)
                .maximumWeight(maxWeight)
                .build());
    }
}
