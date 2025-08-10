import java.io.*;
import java.net.*;
import java.util.*;
public class Server {
    private static final int port=12345;
    private static Set<ClientHandler> client=Collections.synchronizedSet(new HashSet<>());
    public static void main(String[] args){
        System.out.println("Server started on port"+port);
        try(ServerSocket ss=new ServerSocket(port)){
            while (true) {
                Socket s=ss.accept();
                System.out.println("New client connected");
                ClientHandler h=new ClientHandler(s,client);
                client.add(h);
                new Thread(h).start();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
