package com.bookflow.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final ProxyManager<String> proxyManager;

    public boolean isAllowed(
            String key,
            int capacity,
            Duration duration
    ) {

        Bucket bucket = proxyManager.builder()
                .build(
                        key,
                        () -> createBucketConfiguration(capacity, duration)
                );

        return bucket.tryConsume(1);
    }

    private BucketConfiguration createBucketConfiguration(
            int capacity,
            Duration duration
    ) {

        Bandwidth limit = Bandwidth.builder()
                .capacity(capacity)
                .refillGreedy(capacity, duration)
                .build();

        return BucketConfiguration.builder()
                .addLimit(limit)
                .build();
    }
}