import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class HttpRequests {

    private final Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private final int statusCode;
    private final String body;
    private final int contentLength;

    public HttpRequests(String host, int port, String requestTarget) throws IOException {

        Socket socket = new Socket(host, port);

        String request =
                ("GET " + requestTarget + " HTTP/1.1\r\n" +
                "Connection: close\r\n" +
                "Host: " + host + "\r\n" +
                "\r\n")
        ;

        socket.getOutputStream().write(request.getBytes(StandardCharsets.UTF_8));

        String statusLine = readLine(socket);

        statusCode = Integer.parseInt(statusLine.split(" ")[1]);

        String headerLine;
        while ( !(headerLine = readLine(socket)).isEmpty() ) {
            String [] pieces = headerLine.split(":\\s*");
            headers.put(pieces[0], pieces[1]);
        }

        contentLength = Integer.parseInt(getHeader("Content-Length"));

        var body = new byte[contentLength];
        for (int i = 0; i < body.length; i++) {
            body[i] = (byte) socket.getInputStream().read();
        }
        this.body = new String(body, StandardCharsets.UTF_8);
        //System.out.println(body);
    }

    private String readLine(Socket socket) throws IOException {
        int c;
        StringBuilder line = new StringBuilder();
        while ( (c = socket.getInputStream().read()) != '\r') {
            line.append( (char) c);
        }
        socket.getInputStream().read(); // reads the next \n
        //System.out.println(line);
        return line.toString();
    }

    public int getStatusCode() {

        return statusCode;
    }

    public String getHeader(String header) {

        return headers.get(header);
    }

    public String getBody() {
        return body;
    }

    public int getContentLength() {
        return contentLength;
    }

    public static void main(String[] args) throws IOException {
        var socket = new HttpRequests("httpbin.org", 80, "/html");

        System.out.println(socket.body);

    }


}
