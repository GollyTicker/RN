package ClientPackage;

import ServerPackage.ServerOperations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.System.in;

/**
 * Created by Allquantor on 02.04.14.
 * RN_1
 */
public class Client {

    //socket connection
    static Socket serverSocket;
    //send messages to server
    static PrintWriter writerSendOut;
    //read the input stream from server
    static BufferedReader bufferResponse;
    //read StdIn
    static BufferedReader bufferStdInput;

    public static void initialize(String hostName, int port) {

        try {
            serverSocket = new Socket(hostName, port);
            writerSendOut = new PrintWriter(serverSocket.getOutputStream(), true);
            bufferResponse = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            bufferStdInput = new BufferedReader(new InputStreamReader(in)); //System.in)); => Same shit
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void runClient() {

        try {

            //read first Line
            String line = bufferStdInput.readLine();//


            while (line != null) {
                // out send the line to the server
                writerSendOut.println(line);
                writerSendOut.flush();
                System.out.println("Client:sent to server=" + line);

                // we wait of a answer from the server
                String answer = bufferResponse.readLine();
                System.out.println("Client:answer from server=" + answer);
                //check refuse message,disconnect
                if(!IsServerReady(answer)){
                    closeConnection();
                    break;
                }
                // read the next input from the standart IO
                line = bufferStdInput.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Connection succesfull refused");
    }

    public static boolean IsServerReady(String message) {
       return (!(message.equals(ServerOperations.SHUTDOWN_RESPONSE) || (message.equals(ServerOperations.CONNECTION_CLOSE))));
    }

    private static void closeConnection() {
        try {
            //catch.... ahmm close them all!
            writerSendOut.close();
            serverSocket.close();
            bufferStdInput.close();
            bufferResponse.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        String hostName = "127.0.0.1";
        int port = 4444;
        // String hostName = args[0];
        // int portNumber = Integer.parseInt(args[1]);
        initialize(hostName, port);
        runClient();

    }

}
