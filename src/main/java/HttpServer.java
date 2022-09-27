import Johannes.HttpMessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpServer {

    private final ServerSocket serverSocket;
    private final Path serverRoot;

    public HttpServer(int port, Path serverRoot) throws IOException {
        serverSocket = new ServerSocket(port);
        this.serverRoot = serverRoot;
    }

    void start() {

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
        var request = new HttpMessage(clientSocket);
        var requestTarget = request.getStartLine().split(" ")[1];

        var requestPath = serverRoot.resolve(requestTarget.substring(1));

        if (Files.exists(requestPath)) {
            clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                                                  "Connection: close\r\n" +
                                                  "Content-Length: " + requestPath.toFile().length() + "\r\n" +
                                                  "\r\n"
            ).getBytes(StandardCharsets.UTF_8));
            try (var fileInputStream = new FileInputStream(requestPath.toFile())) {
                fileInputStream.transferTo(clientSocket.getOutputStream());
            }
        } else {
            var responseBody = "Unknown URL '" + requestTarget + "'";
            clientSocket.getOutputStream().write(("HTTP/1.1 404 NOT FOUND\r\n" +
                                                  "Content-Type: text/plain\r\n" +
                                                  "Content-Length: " + responseBody.length() + "\r\n" +
                                                  "\r\n" +
                                                  responseBody +
                                                  "\r\n").getBytes(StandardCharsets.UTF_8));
        }
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }


    public static void main(String[] args) throws IOException {
        var serverSocket = new ServerSocket(8080);

        var clientSocket = serverSocket.accept();

        var body = "<html><h1>Hello Bitchæchos</h1></html>";
        var contentLength = body.getBytes().length;

        clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                                              "Content-Type: text/html; charset=utf-8\r\n" +
                                              "Content-Length: " + contentLength + "\r\n" +
                                              "\r\n" +
                                              body).getBytes(StandardCharsets.UTF_8));
    }
}
