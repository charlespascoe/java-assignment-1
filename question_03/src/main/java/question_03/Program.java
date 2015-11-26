package question_03;

import java.io.Console;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {
        Console console = System.console();

        if (console == null) return;

        String inputLine = console.readLine("Please enter some decimal numbers, separated by spaces: ");

        String[] inputArray = inputLine.split("\\s+");

        float min = Float.MAX_VALUE;
        float max = -Float.MAX_VALUE;

        List<Float> floats = new ArrayList<Float>();

        for (String inputNum : inputArray) {
            float num;

            try {
                num = Float.parseFloat(inputNum);
            } catch (Exception ex) {
                console.printf("'%s' isn't a number. Ignoring.%n", inputNum);
                continue;
            }

            floats.add(num);

            if (num < min) min = num;
            if (num > max) max = num;
        }

        if (floats.size() == 0) {
            console.printf("No valid numbers entered!%n");
            return;
        }

        console.printf("Accepted numbers: %s%nMinimum: %s%nMaximum: %s%n", Arrays.deepToString(floats.toArray()), min, max);
    }
}

