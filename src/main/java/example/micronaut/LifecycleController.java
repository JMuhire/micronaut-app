package example.micronaut;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/lifecycle")
public class LifecycleController {

    @Get(value = "/postStart", produces = MediaType.TEXT_PLAIN)
    public HttpResponse postStart() {
        return HttpResponse.ok();
    }

    @Get(value = "/preStop", produces = MediaType.TEXT_PLAIN)
    public HttpResponse preStop() {

        // TODO: this isn't the best way to do it, see ServerStopEndpoint in Micronaut source code
        // https://docs.micronaut.io/latest/guide/index.html#stopEndpoint
        Runtime rt = Runtime.getRuntime();
        rt.exit(0);

        return HttpResponse.ok();
    }
}
