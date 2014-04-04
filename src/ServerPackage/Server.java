package ServerPackage; /**
 * Created by Allquantor on 02.04.14.
 * RN_1
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

                new ServerThread(clientSocket,ServerOperations.threadAnzahl()).run();
            } catch (IOException e) {
                closeConnection();

               // e.printStackTrace();
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


    //if shutdown or more than MAX_CONNECTION then cant accept more
    private static boolean readyToAcceptNewConnection() {
        return ServerOperations.running && ServerOperations.threadAnzahl() <= MAX_CONNECTIONS;
    }

    //close all sockets
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