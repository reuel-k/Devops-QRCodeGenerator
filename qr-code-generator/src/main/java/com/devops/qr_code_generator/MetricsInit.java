package com.devops.qr_code_generator;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class MetricsInit {

    private final MeterRegistry registry;

    public MetricsInit(MeterRegistry registry) {
        this.registry = registry;
    }

    @PostConstruct
    public void init() {
        registry.counter("qrgen.test.metric").increment();
    }
}
