import java.net.*;
import java.io.*;

public class HTTPAsk {
    public static void main( String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));

        while(true){
            try(Socket clientSocket = serverSocket.accept()){
                String[] par;
                int port = 80;
                String hostname = null;
                String string = null;
                StringBuilder res = new StringBuilder();
                
                //initialize streams (in, out)
                BufferedReader inFromClient = 
                    new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream outToClient =
                    new DataOutputStream(clientSocket.getOutputStream());
                
                //read line from client
                par = inFromClient.readLine().split("[\\s/?=&]+");

                for(int i = 0; i < (par.length -1); i++){
                    if(par[i].equals("port"))
                        port = Integer.parseInt(par[i+1]);
                    if(par[i].equals("hostname"))
                        hostname = par[i+1];
                    if(par[i].equals("string")) 
                        string = par[i+1];
                }
                try{
                    res.append("HTTP/1.1 200 OK\r\n\r\n");
                    res.append(TCPClient.askServer(hostname, port, string));
                    outToClient.writeBytes(res.toString());
                    clientSocket.close();
                }catch(UnknownHostException e){
                    outToClient.writeBytes("HTTP/1.1 404 Not Found\r\n\r\n");
                    clientSocket.close();
                }
            }
        }
    }
}