package uk.co.cpascoe.caesar_shift_cracker;

import java.util.List;
import java.util.ArrayList;

public class Utils {
    /**
     * Computes the greatest common devisor (GCD) of 'a' and 'b'
     */
    public static int gcd(int a, int b) {
        if (a == 0) return b;
        if (b == 0) return a;
        return a > b ? Utils.gcd(a % b, b) : Utils.gcd(a, b % a);
    }

    /**
     * Computes Euler's totient of 'x' (number of numbers modulo 'x' coprime to 'x')
     */
    public static int eulerTotient(int x) {
        return Utils.getCoprimes(x).size();
    }

    /**
     * Computes the list of coprimes of x modulo x
     */
    public static List<Integer> getCoprimes(int x) {
        List<Integer> coprimes = new ArrayList<Integer>();

        for (int i = 1; i < x; i++) {
            if (Utils.gcd(i, x) == 1) {
                coprimes.add(i);
            }
        }

        return coprimes;
    }

    /**
     * Computes the value of (base ^ exponent) mod modulus
     */
    public static int powMod(int base, int exponent, int modulus) {
        int result = 1;

        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = (result * base) % modulus;
            }

            base = (base * base) % modulus;
            exponent >>= 1;
        }

        return result;
    }

    /**
     * Computes the multiplicative inverse of x mod modulus
     */
    public static int multiplicativeModuloInverse(int x, int modulus) {
        return Utils.powMod(x, Utils.eulerTotient(modulus) - 1, modulus);
    }

    /**
     * Inserts spaces between groups of letters in the input text
     */
    public static String addSpaces(String text, int groupSize) {
        StringBuilder str = new StringBuilder();

        int counter = 0;

        for (char c : text.toCharArray()) {
            str.append(c);
            counter = (counter + 1) % groupSize;

            if (counter == 0) str.append(" ");
        }

        return str.toString();
    }

    /**
     * Computes the positive solution of x mod modulus
     */
    public static int absoluteModulo(int x, int modulus) {
        // This may look strange, but it guarantees that 0 <= x < modulus
        // as (in Java) -13 % 10 => -3, when it should be 7
        return ((x % modulus) + modulus) % modulus;
    }
}
