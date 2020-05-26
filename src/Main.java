import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        LineProcessingServer server = new LineProcessingServer(10000, "BYE", System.out);
        server.start();
    }
}