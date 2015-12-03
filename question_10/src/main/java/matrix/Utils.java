package matrix;

public class Utils {
    public static String padLeft(String str, int length) {
        return Utils.padLeft(str, length, ' ');
    }

    public static String padLeft(String str, int length, char padding) {
        StringBuilder output = new StringBuilder();

        while (output.length() + str.length() < length) {
            output.append(padding);
        }

        output.append(str);

        return output.toString();
    }

    public static String repeat(char c, int times
}

