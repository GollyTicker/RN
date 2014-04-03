/**
 * Created by Allquantor on 02.04.14.
 */
import com.javafx.tools.doclets.formats.html.SourceToHTMLConverter;

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
                System.out.println("0");

                Socket clientSocket = welcomeSocket.accept();
                InputStream is = clientSocket.getInputStream();

                System.out.println("1");
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(is));

                System.out.println("2");

                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);

                System.out.println("3");

                String line = in.readLine();
                while(line != null) {
                    System.out.println("DAS IST INPUT Line" + sTest);
                    out.println(sTest);
                    out.flush();
                }
                clientSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
