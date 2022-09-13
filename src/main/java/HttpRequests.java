import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

public class HttpRequests {

    private final Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private final int statusCode;

    public HttpRequests(String host, int port, String requestTarget) throws IOException {

        var socket = new Socket(host, port);

        String request =
                ("GET " + requestTarget + " HTTP/1.1\r\n" +
                "Connection: close\r\n" +
                "Host: " + host + "\r\n" +
                "\r\n")
        ;

        socket.getOutputStream().write(request.getBytes());

        String statusLine = readLine(socket);
        statusCode = Integer.parseInt(statusLine.split(" ")[1]);

        String headerLine;
        while ( !(headerLine = readLine(socket)).isEmpty() ) {
            String [] pieces = headerLine.split(":\\s*");
            headers.put(pieces[0], pieces[1]);
        }

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


    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("httpbin.org", 80);
        String request =
                ("GET /html HTTP/1.1\r\n" +
                        "Connection: close\r\n" +
                        "Host: httpbin.org\r\n" +
                        "\r\n");

        socket.getOutputStream().write(request.getBytes());

        int c;
        while ((c = socket.getInputStream().read()) != -1) {
            System.out.print((char) c);
        }
    }


}
