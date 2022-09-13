import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestsTest {

    @Test
    void shouldGetSuccessfulResponseCode() throws IOException {

        HttpRequests client = new HttpRequests("httpbin.org", 80, "/html");
        assertEquals(200, client.getStatusCode());

        //client = new HttpRequests("httpbin.org", 80, "/random");
        //assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldGetFailureResponseCode() throws IOException {
        HttpRequests client = new HttpRequests("httpbin.org", 80, "/status/403");
        assertEquals(403, client.getStatusCode());
    }

    @Test
    void shouldReadResponseHeaders() throws IOException {
        HttpRequests client = new HttpRequests("httpbin.org", 80, "/html");
        assertEquals("text/html; charset=utf-8", client.getHeader("Content-Type"));
    }

    @Test
    void findContentLength() throws IOException {
        HttpRequests client = new HttpRequests("httpbin.org", 80, "/html");
        assertEquals("3741", client.getHeader("Content-Length"));
    }

}
