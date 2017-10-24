package ch.mollusca.samples.reactive.server.checks;

import com.codahale.metrics.health.HealthCheck;
import com.google.common.io.ByteSource;
import com.google.common.io.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationVersionCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        try {
            ByteSource byteSource = Resources.asByteSource(Resources.getResource("version.properties"));

            try (InputStream stream = byteSource.openStream()) {
                Properties properties = new Properties();
                properties.load(stream);

                return Result.healthy(properties.getProperty("VERSION_FULL"));

            } catch (IOException e) {
                return Result.unhealthy("Cannot figure out application version!");
            }
        } catch (Throwable e) {
            return Result.unhealthy("Cannot figure out application version!");
        }
    }
}