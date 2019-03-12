package example.micronaut;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.net.InetAddress;

@Controller("/host")
public class HostController {

    @Get()
    public String host() throws Exception {

        // TODO: add logging in the endopoints?
        return InetAddress.getLocalHost().getHostName();
    }

}
