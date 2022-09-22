import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {

    @Test
    void shouldGive404() throws IOException {
        var server = new HttpServer(0);
        var client = new HttpRequests("localhost", server.getPort(), "/oogabooga");
        assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldReadResponseCode200() throws IOException {
        HttpServer server = new HttpServer(0);
        int port = server.getPort();
        HttpRequests client = new HttpRequests("localhost", port, "/hello");
        assertEquals(200, client.getStatusCode());
    }

}
