package example.micronaut;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ProbeControllerTest {

    private static EmbeddedServer server;
    private static HttpClient client;

    @BeforeClass
    public static void setupServer() {
        server = ApplicationContext.run(EmbeddedServer.class);
        client = server.getApplicationContext().createBean(HttpClient.class, server.getURL());
    }

    @AfterClass
    public static void stopServer() {
        if (server != null) {
            server.stop();
        }
        if (client != null) {
            client.stop();
        }
    }

    // TODO: move this to spock and add test for the full life cycle, also checking the values returned

    @Test
    public void testLiveness() throws Exception {
        HttpRequest request = HttpRequest.GET("/probe/liveness");
        String body = client.toBlocking().retrieve(request);
        assertNotNull(body);
    }

    @Test
    public void testReadiness() throws Exception {
        HttpRequest request = HttpRequest.GET("/probe/readiness");
        String body = client.toBlocking().retrieve(request);
        assertNotNull(body);
    }

    @Test
    public void testUpdateState() throws Exception {
        try {
            HttpRequest request = HttpRequest.POST("/probe", "state=ALIVE")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                    .accept(MediaType.TEXT_PLAIN_TYPE);
            String body = client.toBlocking().retrieve(request);
            assertNotNull(body);

        } finally {
            HttpRequest request = HttpRequest.POST("/probe", "state=READY")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                    .accept(MediaType.TEXT_PLAIN_TYPE);
            client.toBlocking().retrieve(request);

        }
    }
}
