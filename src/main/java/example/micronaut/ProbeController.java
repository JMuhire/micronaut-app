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

}
