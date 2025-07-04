package com.devops.qr_code_generator;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.jvm.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

@Configuration
public class MetricsConfig {

    @Bean
    public MetricRegistry metricRegistry() {
        MetricRegistry registry = new MetricRegistry();

        Graphite graphite = new Graphite(new InetSocketAddress("graphite", 2003));

        GraphiteReporter reporter = GraphiteReporter.forRegistry(registry)
            .prefixedWith("qrcodegen")
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build(graphite);

        reporter.start(10, TimeUnit.SECONDS);
        System.out.println("✅ Dropwizard GraphiteReporter started");

        registry.register("jvm.memory", new MemoryUsageGaugeSet());
        registry.register("jvm.gc", new GarbageCollectorMetricSet());
        registry.register("jvm.threads", new ThreadStatesGaugeSet());
        registry.register("jvm.fd.usage", new FileDescriptorRatioGauge());

        return registry;
    }
}



