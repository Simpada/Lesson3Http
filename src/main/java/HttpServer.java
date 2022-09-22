import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;

public class HttpServer {

    private ServerSocket serverSocket;


    public static int getStatusCode() {

        return 0;
    }


    public int getPort() {
        return serverSocket.getLocalPort();
    }


    public static void main(String[] args) throws IOException {
        var serverSocket = new ServerSocket(8080);

        var clientSocket = serverSocket.accept();

        var body = "<html><h1>Hello Bitchachos</h1></html>";
        var contentLength = body.getBytes().length;

        clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                                              "Content-Type: text/html\r\n" +
                                              "Content-Length: " + contentLength + "\r\n" +
                                              "\r\n" +
                                              body).getBytes(StandardCharsets.UTF_8));

    }
}
