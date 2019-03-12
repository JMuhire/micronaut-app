package example.micronaut;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

@Controller("/probe")
public class ProbeController {

    enum State {
        ZOMBIE,
        ALIVE,
        READY;
    }

    static volatile State STATE = State.READY;

    @Post(consumes = MediaType.APPLICATION_FORM_URLENCODED)
    public String probeUpdate(String state) {

        State prev = STATE;
        STATE = State.valueOf(state);
        return prev.name();
    }

    @Get("liveness")
    public HttpResponse liveness() {

        State s = STATE;
        switch (s) {
            case ZOMBIE:
                return HttpResponse.serverError(s.toString());
            default:
                return HttpResponse.ok(State.ALIVE.toString());
        }
    }

    @Get("readiness")
    public HttpResponse readiness() {

        State s = STATE;
        switch (s) {
            case READY:
                return HttpResponse.ok(State.READY.toString());
            default:
                return HttpResponse.serverError(s.toString());
        }
    }

}
