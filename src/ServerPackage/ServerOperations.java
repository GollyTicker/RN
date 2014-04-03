package ServerPackage;

/**
 * Created by Allquantor on 03.04.14.
 */

public class ServerOperations {
    public final static String CONNECTION_CLOSE = "BYE";
    public final static String LOWERCASE = "LOWERCASE";
    public final static String UPPERCASE = "UPPERCASE";
    public final static String REVERSE = "REVERSE";
    public final static String SHUTDOWN = "SHUTDOWN";

    private ServerOperations() {
    }

    ;

    public static String respondToCommand(String input) {
        if (input.startsWith(CONNECTION_CLOSE)) {
            return CONNECTION_CLOSE;
        }
        if (input.startsWith(LOWERCASE)) {
            return getParam(input).toLowerCase();
        } else if (input.startsWith(UPPERCASE)) {
            return getParam(input).toUpperCase();
        } else if (input.startsWith(REVERSE)) {
            String str = getParam(input);
            //java.util.Collections.reverse(str.toCharArray()));
            String reversed = reverse(str); //(new StringBuffer(str)).reverse().toString();
            System.out.println("input: " + str + ";");
            System.out.println("reversed: " + reversed + ";");
            return reversed;
        } else if (input.startsWith(SHUTDOWN)) {
            String pw = getParam(input);
            // what to do here?
            return "yeah... shutdown?";
        } else {
            return unknownCommand(input);
        }
    }

    // zu einem Kommando der Form "CMD <param>" liefert diese Methode das Param zurueck.
    static String getParam(String str) {
        str = takeWhileNoNewLine(str);
        int seperator = str.indexOf(' ');
        if (seperator == -1) {
            return str;
        } else {
            return str.substring(seperator + 1);
        }
    }

    static String unknownCommand(String input) {
        return "ERROR " + input;
    }

    private static String reverse(String str) {
        /*String reverse = "";
        int length = str.length();
        for( int i = length - 1 ; i >= 0 ; i-- ) {
            reverse = reverse + str.charAt(i);
        }
        return reverse;*/
        return (new StringBuffer(str)).reverse().toString();
    }

    static String takeWhileNoNewLine(String str) {
        int nl = str.indexOf('\\');
        if (nl == -1) {
            return str;
        }

        return str.substring(0, nl - 1);
    }

}
