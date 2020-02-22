import java.net.*;
import java.io.*;

class MyRunnable implements Runnable{
    Socket clientSocket = null;
    public MyRunnable(Socket c){
        this.clientSocket = c;
    }

    public void run(){
        try{
            String[] par;
                Integer port = null;
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

                //extract port, hostname (and and string) from URI 
                for(int i = 0; i < (par.length -1); i++){
                    if(par[i].equals("port"))
                        port = Integer.parseInt(par[++i]);
                    if(par[i].equals("hostname"))
                        hostname = par[++i];
                    if(par[i].equals("string")) 
                        string = par[++i];
                }
                try{
                    Thread.sleep(5000);
                }catch(InterruptedException e){

                }
                if((par[1].equals("ask") || par[2].equals("ask")) && (port != null) && (hostname != null)){
                    try{
                        res.append("HTTP/1.1 200 OK\r\n\r\n");
                        res.append(TCPClient.askServer(hostname, port.intValue(), string));
                        outToClient.writeBytes(res.toString());
                        clientSocket.close();
                    }catch(UnknownHostException e){
                        outToClient.writeBytes("HTTP/1.1 404 Not Found\r\n\r\n");
                        clientSocket.close();
                    }
                }else{
                    outToClient.writeBytes("HTTP/1.1 400 Bad Request\r\n\r\n");
                    clientSocket.close();
                }
        }catch(IOException e){

        }
    }
}