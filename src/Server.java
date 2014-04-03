/**
 * Created by Allquantor on 02.04.14.
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final static String CONNECTION_CLOSE = "BYE";
    private final static String LOWERCASE = "LOWERCASE";
    private final static String UPPERCASE = "UPPERCASE";
    private final static String REVERSE = "REVERSE";
    private final static String SHUTDOWN = "SHUTDOWN";

    public static void  main(String[] args){

        try {
           // int port = Integer.parseInt(args[0]);
            int port = 4444;
            ServerSocket welcomeSocket = new ServerSocket(port);

            //loop
            while(true){
                Socket clientSocket = welcomeSocket.accept();
                //InputStreamReader is = new InputStreamReader(clientSocket.getInputStream());
                InputStream is = clientSocket.getInputStream();
                OutputStream out = clientSocket.getOutputStream();

              //  BufferedReader input = new BufferedReader(is);

                String line = readFromClient(is); //getLineFromInputStream(is);
                while(line != null) {
                    System.out.println("Gelesen: " + line);

                    String resp = respondToCommand(line);

                    sendToClient(resp,out);



                    if (resp.equals(CONNECTION_CLOSE)) {
                        clientSocket.close();
                        break;
                    }
                    line = readFromClient(is); //input.readLine(); //getLineFromInputStream(is);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static String respondToCommand(String input) {
        if (input.startsWith(CONNECTION_CLOSE)) {
            return CONNECTION_CLOSE;
        }
        if (input.startsWith(LOWERCASE)) {
            return getParam(input).toLowerCase();
        }
        else if (input.startsWith(UPPERCASE)) {
            return getParam(input).toUpperCase();
        }
        else if (input.startsWith(REVERSE)) {
            String str = getParam(input);
            return new StringBuffer(str).reverse().toString();
        }
        else if (input.startsWith(SHUTDOWN)) {
            String pw = getParam(input);
            // what to do here?
            return "yeah... shutdown?";
        }
        else {
            return unknownCommand(input);
        }
    }

    static String takeWhileNoNewLine(String str) {
        int nl = str.indexOf('\\');
        if(nl == -1) {
            return str;
        }

        return str.substring(0, nl - 1);
    }

    // zu einem Kommando der Form "CMD <param>" liefert diese Methode das Param zurueck.
    static String getParam(String str) {
        str = takeWhileNoNewLine(str);
        int seperator = str.indexOf(' ');
        if (seperator == -1) {
            return str;
        }
        else {
            return str.substring(seperator + 1);
        }
    }

    static String unknownCommand(String input){
        return "ERROR " + input;
    }

    static String getLineFromInputStream(InputStreamReader is){
        String erg = "";
        char[] input = new char[255];
        try {
            is.read(input);
            erg = new String(input) + "\n";
        } catch (IOException e) {
            e.printStackTrace();
            erg = null;
        }

        return erg;

    }

    static String readFromClient(InputStream input) {
        int read;
        byte[] byteArray = new byte[255];
        boolean keepGo = true;

        for(int i =0; i < byteArray.length && keepGo == true; i ++) {
            try {
                read =  input.read();
                if(read == -1 || read == 10) {
                    keepGo = false;
                } else {
                    byteArray[i] = (byte) read;
                }
            } catch (IOException e) {
                keepGo = false;
            }
        }


        try {
            return (new String(byteArray,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void sendToClient(String message,OutputStream output){
        try {

            byte[] byteArray = (message + "\n").getBytes("UTF-8");
            output.write(byteArray,0,byteArray.length);

        } catch (IOException e) {
            e.printStackTrace();
        }



    }




}
