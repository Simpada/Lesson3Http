import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestsTest {

    @Test
    void shouldGetSuccessfulResponseCode() throws IOException {

        HttpRequests client = new HttpRequests("httpbin.org", 80, "/html");

        assertEquals(200, client.getStatusCode());

    }

}
