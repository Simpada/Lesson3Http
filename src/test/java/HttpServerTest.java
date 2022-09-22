import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {

    @Test
    void shouldGive404() throws IOException {
        var server = new HttpServer();
        var client = new HttpRequests("localhost", server.getPort(), "/oogabooga");
        assertEquals(404, client.getStatusCode());
    }

}
