package uk.co.cpascoe.caesar_shift_cracker;

import java.lang.Math;
import java.lang.Double;

public class CaesarShiftCracker {
    private Alphabet alphabet;

    public CaesarShiftCracker(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    public String crack(String ciphertext) throws Exception {
        double[] probs = this.alphabet.computeProbabilities(ciphertext);

        int bestShift = 0;
        double bestDelta = Double.MAX_VALUE;

        for (int i = 0; i < this.alphabet.length(); i++) {
            double delta = this.getFrequencyDelta(i, probs);

            if (delta < bestDelta) {
                bestDelta = delta;
                bestShift = i;
            }
        }

        System.out.println(bestShift);

        return this.decrypt(ciphertext, bestShift);
    }

    public String decrypt(String ciphertext, int shift) throws Exception {
        StringBuilder str = new StringBuilder();

        int len = this.alphabet.length();
        int inverseShift = len - shift;

        for (char c : this.alphabet.stripToAlphabet(ciphertext).toCharArray()) {
            str.append(this.alphabet.getChar((this.alphabet.indexOf(c) + inverseShift) % len));
        }

        return str.toString();
    }

    private double getFrequencyDelta(int displacement, double[] observedProbabilities) throws Exception {
        double[] expectedProbabilities = this.alphabet.getExpectedProbabilities();

        if (expectedProbabilities.length != observedProbabilities.length) {
            throw new Exception("Incorrect length of observedProbabilities");
        }

        double delta = 0;

        for (int i = 0; i < expectedProbabilities.length; i++) {
            delta += Math.abs(expectedProbabilities[i] - observedProbabilities[(i + displacement) % expectedProbabilities.length]);
        }

        return delta;
    }
}
