package uk.co.cpascoe.caesar_shift_cracker;

import java.util.List;
import java.util.ArrayList;

public class Utils {
    public static int gcd(int a, int b) {
        if (a == 0) return b;
        if (b == 0) return a;
        return a > b ? Utils.gcd(a % b, b) : Utils.gcd(a, b % a);
    }

    public static int eulerTotient(int x) {
        return Utils.getCoprimes(x).size();
    }

    public static List<Integer> getCoprimes(int x) {
        List<Integer> coprimes = new ArrayList<Integer>();

        for (int i = 1; i < x; i++) {
            if (Utils.gcd(i, x) == 1) {
                coprimes.add(i);
            }
        }

        return coprimes;
    }

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

    public static int multiplicativeModuloInverse(int x, int modulus) {
        return Utils.powMod(x, Utils.eulerTotient(modulus) - 1, modulus);
    }
}
