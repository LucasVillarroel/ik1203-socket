import java.net.*;
import java.io.*;

public class HTTPEcho {
    public static void main( String[] args) throws IOException{
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);
        while(true){
            try(Socket cSocket = serverSocket.accept()){
                String inLine;
                StringBuilder sb = new StringBuilder();
                sb.append("HTTP/1.1 200 OK\r\n\r\n");
                
                //initialize streams (in, out)
                BufferedReader inFromClient = 
                    new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
                DataOutputStream outToClient =
                    new DataOutputStream(cSocket.getOutputStream());

                //read from client
                while(((inLine = inFromClient.readLine()) != null) && !(inLine.isEmpty())){
                    sb.append(inLine + "\r\n");
                }
                
                //echo to client
                outToClient.writeBytes(sb.toString());

                //close streams, socket
                inFromClient.close();
                outToClient.close();
                cSocket.close();

            }catch(IOException e){
            }
        }
    }
}