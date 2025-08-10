
import java.io.*;
import java.net.*;
import java.util.*;
public class ClientHandler implements Runnable{
    private Socket s;
    private BufferedReader br;
    private PrintWriter pw;
    private Set<ClientHandler> client;
    private String name;
    public ClientHandler(Socket s,Set<ClientHandler> client){
        this.s=s;
        this.client=client;
    }
    @Override
    public void run(){
        try{
            br=new BufferedReader(
                new InputStreamReader(s.getInputStream()));
                pw=new PrintWriter(s.getOutputStream(),true);
                pw.println("Enter your name:");
                name=br.readLine();
                broadcast(name+"has joined the Chat");
                String msg;
                while ((msg=br.readLine())!=null) {
                    broadcast(name+": "+msg);
                    
                }
        }
        catch(IOException e){
            System.out.println(name+"disconnected");
        }
        finally{
            client.remove(this);
            broadcast(name+" has left the chat");
            try{
                s.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            
        }
    }
    private void broadcast(String msg){
        synchronized(client){
            for(ClientHandler c:client){
                if(c!=this){
                    c.pw.println(msg);
                }
            }
        }
    }
}
