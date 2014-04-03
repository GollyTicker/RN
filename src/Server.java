/**
 * Created by Allquantor on 02.04.14.
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final String CONNECTION_CLOSE = "BYE";
    private final String LOWERCASE = "LOWERCASE";
    private final String UPPERRCASE = "UPPERCASE";
    private final String REVERSE = "REVERSE";
    private final String SHUTDOWN = "SHUTDOWN";

    public static void  main(String[] args){

        try {
           // int port = Integer.parseInt(args[0]);
            int port = 4444;
            ServerSocket welcomeSocket = new ServerSocket(port);

            //loop
            while(true){
                //System.out.println("0");

                Socket clientSocket = welcomeSocket.accept();
                InputStream is = clientSocket.getInputStream();

                //System.out.println("1");
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(is));

                //System.out.println("2");

                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);

                //System.out.println("3");

                String line = in.readLine();
                while(line != null) {
                    System.out.println("Gelesen: " + line);

                    String resp = respondToCommand(line);

                    out.println(resp);
                    out.flush();

                    if (resp.equals(CONNECTION_CLOSE)) {
                        clientSocket.close();
                        break;
                    }
                    line = in.readLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    String respondToCommand(String input) {
        if (input.startsWith(CONNECTION_CLOSE)) {
            return CONNECTION_CLOSE;
        }
        if (input.startsWith(LOWERCASE)) {
            return input.getParam().toUpperCase();
        }
        else if (input.startsWith(UPPERCASE)) {
            return input.getParam().toLowerCase();
        }
        else if (input.startsWith(REVERSE)) {
            String str = inout.getParam();
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

    // zu einem Kommando der Form "CMD <param>" liefert diese Methode das Param zurueck.
    String getParam(String str) {
        int seperator = str.indexOf(' ');
        if (seperator == -1) {
            return str;
        }
        else {
            return str.substring(seperator + 1);
        }
    }

}
