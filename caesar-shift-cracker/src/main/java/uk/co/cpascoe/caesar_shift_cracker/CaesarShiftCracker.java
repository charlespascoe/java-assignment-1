package uk.co.cpascoe.caesar_shift_cracker;

import java.lang.Math;

public class CaesarShiftCracker {
    private Alphabet alphabet;
    private boolean uppercaseOnly;

    public CaesarShiftCracker(Alphabet alphabet, boolean uppercaseOnly) {
        this.alphabet = alphabet;
        this.uppercaseOnly = uppercaseOnly;
    }

    public String crack(String ciphertext) {
        double[] probs = this.alphabet.computeProbabilities(ciphertext);

        int bestShift = 0;
        double bestDelta = Double.MAX;

        for (int i = 0; i < this.alphabet.length(); i++) {
            double delta = this.getFrequencyDelta(i, probs);

            if (delta < bestDelta) {
                bestDelta = delta;
                bestShift = i;
            }
        }

        return this.decrypt(ciphertext, bestShift);
    }

    public String decrypt(String ciphertext, int shift) {
        StringBuilder str = new StringBuilder();

        int len = this.alphabet.length();
        int inverseShift = len - shift;

        for (char c : this.alphabet.stripToAlphabet(ciphertext)) {
            str.append(this.alphabet.getChar((this.alphabet.indexOf(c) + inverseShift) % len));
        }

        return str.toString();
    }

    private double getFrequencyDelta(int displacement, double observedFrequencies) {
        double[] expectedFrequencies = this.alphabet.getExpectedFrequencies();

        if (expectedFrequencies.length != observedFrequencies.length) {
            throw new Exception("Incorrect length of observedFrequencies");
        }

        double delta = 0;

        for (int i = 0; i < expectedFrequencies.length; i++) {
            delta += Math.abs(expectedFrequencies[(i + displacement) % expectedFrequencies.length] - observedFrequencies[i]);
        }

        return delta;
    }
}
