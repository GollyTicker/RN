import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Allquantor on 02.04.14.
 */
public class Client {
    public static void main(String[] args) {

        int port = 4444;
       // String hostName = args[0];
       // int portNumber = Integer.parseInt(args[1]);
        String hostName = "127.0.0.1";

        try {


            Socket serverSocket = new Socket(hostName, port);

            PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(serverSocket.getInputStream()));

            BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
            String line = reader.readLine();


            while(line != null){
                System.out.println("0Client");
                out.println(line);
                out.flush();
                System.out.println("Sent to server: " + line);
                String answer = in.readLine();
                System.out.println("Answer from server:" + answer);
                line = reader.readLine();
            }

        }catch(IOException e){
            e.printStackTrace();
        }

    }

}
