import java.net.*;
import java.io.*;

public class ConcHTTPAsk {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            MyRunnable runnable = new MyRunnable(serverSocket.accept());
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }
}