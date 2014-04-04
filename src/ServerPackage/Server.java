package ServerPackage; /**
 * Created by Allquantor on 02.04.14.
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final static int MAX_CONNECTIONS = 600;
    static ServerSocket welcomeSocket;
    static Socket clientSocket;

    public static void initializeServer(int port) {
        try {
            welcomeSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void runServer() {


        while (readyToAcceptNewConnection()) {
            try {
                clientSocket = welcomeSocket.accept();
                ServerOperations.threadAnzahlIncrease();
                (new ServerThread(clientSocket)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Shutdown
        if (!ServerOperations.running) closeConnection();
    }


    public static void main(String[] args) {
        //int port = Integer.parseInt(args[0]);
        int port = 4444;
        initializeServer(port);

        runServer();

        //maybe a main loop for the process and if shutdown destroy the object?

    }

    private static boolean readyToAcceptNewConnection() {
        return ServerOperations.running && ServerOperations.threadAnzahl() <= MAX_CONNECTIONS;
    }

    private static void closeConnection() {
        try {
            System.out.println("Server: starting shutdown process");
            welcomeSocket.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
