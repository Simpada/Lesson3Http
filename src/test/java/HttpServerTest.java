import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {

    private final Path serverRoot = Path.of("target", "test-files");

    @Test
    void shouldGive404() throws IOException {
        var server = new HttpServer(0, serverRoot);
        server.start();
        var client = new HttpRequests("localhost", server.getPort(), "/oogabooga");
        assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldReadResponseCode200() throws IOException {
        Files.createDirectories(serverRoot);
        Path file = serverRoot.resolve("hello.txt");
        String content = "Hello " + LocalDateTime.now();
        Files.writeString(file, content);
        var server = new HttpServer(0, serverRoot);
        server.start();
        var client = new HttpRequests("localhost", server.getPort(), "/" + file.getFileName());
        assertEquals(200, client.getStatusCode());
    }

    @Test
    void shouldParseRequestParameters() throws IOException {
        /*Files.createDirectories(serverRoot);
        Path file = serverRoot.resolve("no-file-here.txt");
        String content = "File not found";
        Files.writeString(file, content); */

        HttpServer server = new HttpServer(0, serverRoot);
        server.start();
        int port = server.getPort();

        var client = new HttpRequests("localhost", port, "/no-file-here");

        assertEquals("Unknown URL '/no-file-here'", client.getBody());
    }

    @Test
    void shouldReadFileFromDisk() throws IOException {
        HttpServer server = new HttpServer(0, serverRoot);
        server.start();
        int port = server.getPort();
        String fileContent = "A file created at " + LocalDateTime.now();
        Files.write(Paths.get("target/test-files/example-file.txt"), fileContent.getBytes());
        var client = new HttpRequests("localhost", port, "/example-file.txt");
        assertEquals(fileContent, client.getBody());
    }


}
