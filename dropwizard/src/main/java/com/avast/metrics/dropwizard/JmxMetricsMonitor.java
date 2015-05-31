package com.avast.metrics.dropwizard;

import com.avast.metrics.api.Monitor;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.ObjectNameFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JmxMetricsMonitor extends MetricsMonitor {

    private final JmxReporter reporter;

    public JmxMetricsMonitor(String domain) {
        this(TreeObjectNameFactory.getInstance(), domain, false);
    }

    public JmxMetricsMonitor(String domain, boolean generateUniqueNames) {
        this(TreeObjectNameFactory.getInstance(), domain, generateUniqueNames);
    }

    public JmxMetricsMonitor(ObjectNameFactory objectNameFactory, String domain, boolean generateUniqueNames) {
        super(generateUniqueNames);
        this.reporter = JmxReporter
            .forRegistry(registry)
            .inDomain(domain)
            .createsObjectNamesWith(objectNameFactory)
            .build();
        this.reporter.start();
    }

    private JmxMetricsMonitor(JmxMetricsMonitor original, String name) {
        super(original, name);
        this.reporter = original.reporter;
    }

    @Override
    public Monitor named(String name) {
        return new JmxMetricsMonitor(this, name);
    }

    @Override
    protected String separator() {
        return TreeObjectNameFactory.SEPARATOR;
    }

    @Override
    protected String constructMetricName(Optional<String> finalName) {
        List<String> copy = new ArrayList<>(names);
        finalName.ifPresent(copy::add);
        return copy.stream().collect(Collectors.joining(separator()));
    }
}
