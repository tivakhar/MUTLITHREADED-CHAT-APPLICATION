
import java.io.*;
import java.net.*;
public class Client {
    private static final String ip="localhost";
    private static final int port=12345;
    public static void main(String[] args){
        try(
            Socket s=new Socket(ip,port);
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
            BufferedReader in=new BufferedReader(new InputStreamReader(s.getInputStream()))
        ){
            System.out.println("Connected to chat server!");
            Thread thread=new Thread(()->{
                String msg;
                try{
                    while ((msg=in.readLine())!=null) {
                        System.out.println(msg);
                    }
                } catch(IOException e){
                    System.out.println("Server connection lost");
                }
            });
            thread.start();
            String user;
            while ((user=br.readLine())!=null) {
                pw.println(user);
                if(user.equalsIgnoreCase("exit")){
                    break;
                }
            }
        }
        catch(IOException e){
            System.out.println("Could not connect to server.");
            e.printStackTrace();
        }
    }
}
