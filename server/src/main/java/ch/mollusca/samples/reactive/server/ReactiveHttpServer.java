package ch.mollusca.samples.reactive.server;

import ch.mollusca.samples.reactive.server.checks.ApplicationVersionCheck;
import ch.mollusca.samples.reactive.server.checks.DatabaseIsReachableCheck;
import ch.mollusca.samples.reactive.server.resources.FibonacciEndpoint;
import ch.mollusca.samples.reactive.server.resources.HelloResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReactiveHttpServer extends Application<ReactiveHttpServerConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveHttpServer.class);

    public static void main(String... args) throws Exception {
        LOGGER.info("Booting up {} on {}", ReactiveHttpServer.class.getSimpleName());
        new ReactiveHttpServer().run(args);
    }

    @Override
    public void initialize(Bootstrap<ReactiveHttpServerConfiguration> bootstrap) {

    }

    @Override
    public void run(ReactiveHttpServerConfiguration configuration, Environment environment) throws Exception {
        registerResources(environment);
        registerHealthChecks(configuration, environment);
    }

    private void registerHealthChecks(ReactiveHttpServerConfiguration configuration, Environment environment) {
        environment.healthChecks().register("database-reachable", new DatabaseIsReachableCheck(configuration));
        environment.healthChecks().register("application-version", new ApplicationVersionCheck());
    }

    private void registerResources(Environment environment) {
        environment.jersey().register(HelloResource.class);
        environment.jersey().register(FibonacciEndpoint.class);
    }
}
