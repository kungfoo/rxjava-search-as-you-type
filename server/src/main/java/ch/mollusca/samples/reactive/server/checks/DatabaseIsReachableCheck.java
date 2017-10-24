package ch.mollusca.samples.reactive.server.checks;

import ch.mollusca.samples.reactive.server.ReactiveHttpServerConfiguration;
import com.codahale.metrics.health.HealthCheck;

import java.util.Objects;

public class DatabaseIsReachableCheck extends HealthCheck {

    private final ReactiveHttpServerConfiguration configuration;

    public DatabaseIsReachableCheck(ReactiveHttpServerConfiguration configuration) {
        this.configuration = Objects.requireNonNull(configuration, "Configuration can never be null!");
    }

    @Override
    protected Result check() throws Exception {
        // TODO: do some meaningful checking here. :)
        return Result.healthy("Can haz database connection!");
    }
}
