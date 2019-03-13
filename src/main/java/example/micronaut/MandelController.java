package example.micronaut;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.prometheus.client.Histogram;

@Controller("/mandel")
public class MandelController {

    private final MandelService mandelService;

    public MandelController(MandelService mandelService) {
        this.mandelService = mandelService;
    }

    @Get("/work")
    public String work() throws Exception {

        Histogram.Timer timer = Metrics.WORK_REQUEST_LATENCY.startTimer();
        try {
            return mandelService.render();
        } finally {
           timer.observeDuration();
        }
    }

}
