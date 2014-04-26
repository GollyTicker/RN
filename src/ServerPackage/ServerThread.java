package ServerPackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by Allquantor on 03.04.14.
 * RN_1
 */
public class ServerThread extends Thread {

    Socket clientSocket;
    final int threadID;
    InputStream inputStream;
    OutputStream outputStream;


    public ServerThread(Socket socket,int threadID) {
        clientSocket = socket;
        this.threadID = threadID;
    }

    private void initializeThread(){
        try {
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        initializeThread();
        String resp = null;

        if(!clientSocket.isClosed()) {
            do {

                String line = readFromClient();
                if(line == null) break;
                System.out.println("ServerThread:Gelesen= " + line);
                resp = ServerOperations.respondToCommand(line);
                sendToClient(resp);

            } while (!isConnectionClosed(resp));
        }

        closeConnectionAndStopThread();
        System.out.println("ServerThread:Connection refused and thread ID=" + this.threadID + " stopped");
        ServerOperations.threadAnzahlDecrease();
    }



    boolean isConnectionClosed(String resp){
        return resp.equals(ServerOperations.CONNECTION_CLOSE) || resp.equals(ServerOperations.SHUTDOWN_RESPONSE) || clientSocket.isClosed() || !clientSocket.isConnected();
    }


    void closeConnectionAndStopThread(){
        try {
            inputStream.close();
            outputStream.close();
            clientSocket.close();
            //decrease thread anzahl
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
     String readFromClient() {
        int read;
        byte[] byteArray = new byte[255];
        boolean keepGo = true;

        for (int i = 0; i < byteArray.length && keepGo == true; i++) {
            try {
                read = inputStream.read();

                if (read == -1 || read == 10) {
                    keepGo = false;
                } else {
                    byteArray[i] = (byte) read;
                }
            } catch (IOException e) {
                keepGo = false;
                return null;
            }
        }

        try {
            return (new String(byteArray, "UTF-8")).trim();
        } catch (UnsupportedEncodingException e) {
            return null;
            //e.printStackTrace();
        }
    }

     void sendToClient(String message) {
        try {

            //System.out.println("ClientSocket is connected:" + clientSocket.isConnected());
            //System.out.println("ClientSocket is closed:" + clientSocket.isConnected());
            //System.out.println("ClientSocket is bound:" + clientSocket.isBound());
            //System.out.println("ClientSocket is inputS:" + clientSocket.isInputShutdown());
            //System.out.println("ClientSocket is outputS:" + clientSocket.isOutputShutdown());

            if(!clientSocket.isClosed()){
                byte[] byteArray = (message + "\n").getBytes("UTF-8");
                outputStream.write(byteArray, 0, byteArray.length);
            }

        } catch (Exception e) {
            closeConnectionAndStopThread();
           // e.printStackTrace();
        }

    }
}
