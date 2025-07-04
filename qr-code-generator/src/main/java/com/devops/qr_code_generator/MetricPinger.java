package com.devops.qr_code_generator;

import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.context.annotation.Configuration;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Counter;

@Configuration
public class MetricPinger {

    private final MetricRegistry metricRegistry;
    private Counter heartbeat;

    public MetricPinger(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @PostConstruct
    public void init() {
        this.heartbeat = metricRegistry.counter("qrgen.heartbeat");
        System.out.println("✅ MetricPinger initialized with Dropwizard");
    }

    @Scheduled(fixedRate = 5000)
    public void pushMetrics() {
        heartbeat.inc();
        System.out.println("📡 Dropwizard heartbeat pushed");
    }
}

