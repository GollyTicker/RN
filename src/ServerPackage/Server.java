package ServerPackage; /**
 * Created by Allquantor on 02.04.14.
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] args) {

        try {
            int port = Integer.parseInt(args[0]);
            //int port = 4444;
            ServerSocket welcomeSocket = new ServerSocket(port);

            //loop
            while (true) {
                Socket clientSocket = welcomeSocket.accept();
                (new ServerThread(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
