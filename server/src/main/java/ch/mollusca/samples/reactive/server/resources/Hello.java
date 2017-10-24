package ch.mollusca.samples.reactive.server.resources;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class Hello {

    private static AtomicInteger counter = new AtomicInteger(1);

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        return new Saying(counter.getAndIncrement(), "Hello " + name.orElse("stranger") + "!");
    }
}

