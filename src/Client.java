import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Allquantor on 02.04.14.
 */
public class Client {
    public static void main(String[] args) {


/* todo Change BufferReader to ByteArray max 255bytes  */


        int port = 4444;
       // String hostName = args[0];
       // int portNumber = Integer.parseInt(args[1]);
        String hostName = "127.0.0.1";

        try {

            byte[] input = new byte[255];

            //open new Socket connection
            Socket serverSocket = new Socket(hostName, port);

            // out stream send messages to the server
            PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
            // buffer reader read the input stream and get the answer from server
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(serverSocket.getInputStream()));




            // buffer reader read the input from StandartInput (in)
            BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
            // read the first line from standart input

            System.in.read(input);
            //int bytesReadet = serverSocket.getInputStream().read(input);


            //String line = reader.readLine();
            String line = new String(input);


            System.out.println("das ist die line" + line);

            while(line != null){

                System.out.println("0Client");
                // out send the line to the server
                out.println(line);
                out.flush();
                System.out.println("Sent to server: " + line);
                // we wait of a answer from the server
                String answer = in.readLine();
                System.out.println("Answer from server:" + answer);
                // read the next input from the standart IO
                line = reader.readLine();
            }

        }catch(IOException e){
            e.printStackTrace();
        }

    }

}
