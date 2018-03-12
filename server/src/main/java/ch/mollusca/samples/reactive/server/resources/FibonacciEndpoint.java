package ch.mollusca.samples.reactive.server.resources;


import ch.mollusca.samples.reactive.api.dtos.FibonacciSequence;
import ch.mollusca.samples.reactive.server.domain.Fibonacci;
import com.codahale.metrics.annotation.Timed;
import io.reactivex.Single;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Produces(MediaType.APPLICATION_JSON)
@Path("/fibonacci")
public class FibonacciEndpoint {

    @GET
    @Timed
    public FibonacciSequence fibonacci(@QueryParam("n") Optional<Integer> n) {
        Single<List<BigInteger>> result = Fibonacci.sequence()
                .take(n.orElse(100))
                .toList();

        return new FibonacciSequence(result.blockingGet());
    }

}
