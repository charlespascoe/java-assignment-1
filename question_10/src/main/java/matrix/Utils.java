package matrix;

public class Utils {
    public static String padLeft(String str, int length) {
        return Utils.padLeft(str, length, ' ');
    }

    public static String padLeft(String str, int length, char padding) {
        return Utils.repeat(padding, length - str.length()) + str;
    }

    public static String repeat(char c, int times) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < times; i++) {
            str.append(c);
        }

        return str.toString();
    }
}

