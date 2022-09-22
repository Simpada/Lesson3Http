import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class HttpServer {

    private ServerSocket serverSocket;
    //private final Path serverRoot;

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        //this.serverRoot = serverRoot;
        start();
    }

    private void start() {

        new Thread(() -> {
            try {
                var clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();

    }

    private void handleClient (Socket clientSocket) throws IOException {

        var responseBody = "Unknown URL '/oogabooga'";
        clientSocket.getOutputStream().write(("HTTP/1.1 404 NOT FOUND\r\n" +
                                              "Content-Type: text/plain\r\n" +
                                              "Content-Length: " + responseBody.length() + "\r\n" +
                                              "\r\n" +
                                              responseBody +
                                              "\r\n").getBytes(StandardCharsets.UTF_8));

    }

    public static int getStatusCode() {

        return 0;
    }


    public int getPort() {
        return serverSocket.getLocalPort();
    }


    public static void main(String[] args) throws IOException {
        var serverSocket = new ServerSocket(8080);

        var clientSocket = serverSocket.accept();

        var body = "<html><h1>Hello Bitchachos øæå</h1></html>";
        var contentLength = body.getBytes().length;

        clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                                              "Content-Type: text/html\r\n" +
                                              "Content-Length: " + contentLength + "\r\n" +
                                              "\r\n" +
                                              body).getBytes(StandardCharsets.UTF_8));

    }
}
