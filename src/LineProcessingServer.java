import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class LineProcessingServer {
    private final int port;
    private final String quitCommand;
    private final PrintStream ps;

    public LineProcessingServer(int port, String quitCommand, OutputStream os) {
        this.port = port;
        this.quitCommand = quitCommand;
        ps = new PrintStream(os);
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket = serverSocket.accept();
            handleClient(socket);
        }
    }

    private void handleClient(Socket socket) throws IOException {
        ps.printf("`[%1$tY-%1$tm-%1$td %1$tT] Connection from %2$s`.%n", System.currentTimeMillis(), socket.getInetAddress());
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        int requestsCounter = 0;
        while (true) {
            String line = br.readLine();
            if (line.toUpperCase().equals(quitCommand)) {
                break;
            }
            bw.write("The number of words sent is: " + countWords(line) + "\n");
            bw.flush();
            requestsCounter = requestsCounter + 1;
        }
        socket.close();
        ps.printf("`[%1$tY-%1$tm-%1$td %1$tT] Disconnection of %2$s after %3$d requests`.%n", System.currentTimeMillis(),
                socket.getInetAddress(), requestsCounter);
    }

    private int countWords(String str) {
        StringTokenizer defaultTokenizer = new StringTokenizer(str, " ,.?!:;");

        int count = 0;
        while (defaultTokenizer.hasMoreTokens()) {
            System.out.println(defaultTokenizer.nextToken());
            count ++;
        }

        return count;
    }
}