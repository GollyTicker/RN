package ServerPackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by Allquantor on 03.04.14.
 */
public class ServerThread extends Thread {

    Socket clientSocket;


    public ServerThread(Socket socket) {
        clientSocket = socket;
    }

    public void run() {
        try {

            InputStream is = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();
            // read first line
            String line = readFromClient(is);

            while (line != null) {
                System.out.println(getClass().getName());
                System.out.println("ServerThread:Gelesen= " + line);

                String resp = ServerOperations.respondToCommand(line);

                sendToClient(resp, out);

                if (resp.equals(ServerOperations.CONNECTION_CLOSE) || resp.equals(ServerOperations.SHUTDOWN_RESPONSE)) {
                    is.close();
                    out.close();
                    clientSocket.close();
                    line = null;
                    break;
                }
                line = readFromClient(is);

            }
        } catch (IOException e) {
            // thread wird beendet
            ServerOperations.threadAnzahlDecrease();
        }
    }


    static String readFromClient(InputStream input) {
        int read;
        byte[] byteArray = new byte[255];
        boolean keepGo = true;

        for (int i = 0; i < byteArray.length && keepGo == true; i++) {
            try {
                read = input.read();
                if (read == -1 || read == 10) {
                    keepGo = false;
                } else {
                    byteArray[i] = (byte) read;
                }
            } catch (IOException e) {
                keepGo = false;
            }
        }

        try {
            return (new String(byteArray, "UTF-8")).trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void sendToClient(String message, OutputStream output) {
        try {

            byte[] byteArray = (message + "\n").getBytes("UTF-8");
            output.write(byteArray, 0, byteArray.length);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
