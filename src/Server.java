/**
 * Created by Allquantor on 02.04.14.
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void  main(String[] args){

        try {
           // int port = Integer.parseInt(args[0]);
            int port = 4444;
            ServerSocket welcomeSocket = new ServerSocket(port);

            //loop
            while(true){
                Socket clientSocket = welcomeSocket.accept();
                InputStream is = clientSocket.getInputStream();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(is));

                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);


                //print output
                String sTest = in.readLine();

                System.out.println("DAS IST INPUT STREAM;" + sTest);
                out.print(sTest);
                out.flush();
                clientSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
