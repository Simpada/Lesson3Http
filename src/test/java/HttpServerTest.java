import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {

    private Path serverRoot = Path.of("target", "test-files");

    @Test
    void shouldGive404() throws IOException {
        var server = new HttpServer(0, serverRoot);
        var client = new HttpRequestResult("localhost", server.getPort(), "/oogabooga");
        assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldReadResponseCode200() throws IOException {
        Files.createDirectories(serverRoot);
        Path file = serverRoot.resolve("hello.txt");
        String content = "Hello " + LocalDateTime.now();
        Files.writeString(file, content);
        var server = new HttpServer(0, serverRoot);
        var client = new HttpRequestResult("localhost", server.getPort(), "/" + file.getFileName());
        assertEquals(200, client.getStatusCode());
    }

}
