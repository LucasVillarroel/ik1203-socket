import java.net.*;
import java.io.*;

public class TCPClient {
    public static String askServer(String hostname, int port, String toServer) throws IOException {
        if (toServer == null)
            return askServer(hostname, port);

        StringBuilder sb = new StringBuilder();
        String inLine;
        int counter = 0;

        Socket socket = new Socket(hostname, port);
        socket.setSoTimeout(3000);

        DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outToServer.writeBytes(toServer + '\n');
        try {
            while (((inLine = inFromServer.readLine()) != null) && (inLine != "\n") && (counter < 1000)) {
                sb.append(inLine + '\n');
                counter++;
            }
            socket.close();
            return sb.toString();
        } catch (SocketTimeoutException e) {
            socket.close();
            return sb.toString();
        }
    }

    public static String askServer(String hostname, int port) throws IOException {
        StringBuilder sb = new StringBuilder();
        String inLine;
        int counter = 0;

        Socket socket = new Socket(hostname, port);
        socket.setSoTimeout(3000);

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            while (((inLine = inFromServer.readLine()) != null) && (inLine != "\n") && (counter < 1000)) {
                sb.append(inLine + '\n');
                counter++;
            }
            socket.close();
            return sb.toString();
        } catch (SocketTimeoutException e) {
            socket.close();
            return sb.toString();
        }
    }
}