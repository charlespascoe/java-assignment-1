package uk.co.cpascoe.caesar_shift_cracker;

import java.lang.Math;
import java.lang.Double;

public class CaesarShiftCracker {
    public static class Result {
        public final int bestShift;
        public final double bestDelta;

        public Result(int bestShift, int bestDelta) {
            this.bestShift = bestShift;
            this.bestDelta = bestDelta;
        }
    }

    private Alphabet alphabet;

    public CaesarShiftCracker(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    public Result guessKey(String ciphertext) throws Exception {
        return this.guessKey(this.alphabet.computeProbabilities(ciphertext));
    }

    public Result guessKey(double[] props) throws Exception {
        int bestShift = 0;
        double bestDelta = Double.MAX_VALUE;

        for (int i = 0; i < this.alphabet.length(); i++) {
            double delta = this.getFrequencyDelta(i, probs);

            if (delta < bestDelta) {
                bestDelta = delta;
                bestShift = i;
            }
        }

        return new CaesarShiftCracker.Result(bestShift, bestDelta);
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
