package uk.co.cpascoe.caesar_shift_cracker;

public class AffineShiftCracker {
    public static class Result {
        public final int bestA;
        public final int bestB;
        public final double bestDelta;

        public Result(int bestA, int bestB, double bestDelta) {
            this.bestA = bestA;
            this.bestB = bestB;
            this.bestDelta = bestDelta;
        }
    }

    private Alphabet alphabet;

    public AffineShiftCracker(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    public AffineShiftCracker.Result guessKey(String ciphertext) throws Exception {
        return this.guessKey(this.alphabet.computeProbabilities(ciphertext));
    }

    public Result guessKey(double[] probs) throws Exception {
        int bestA = 1;
        int bestB = 0;
        double bestDelta = Double.MAX_VALUE;

        int modulus = this.alphabet.length();

        CaesarShiftCracker csc = new CaesarShiftCracker(this.alphabet);

        for (int inverseA : Utils.getCoprimes(modulus)) {
            CaesarShiftCracker.Result res = csc.guessKey(this.mapProbabilities(probs, inverseA));
            if (res.bestDelta < bestDelta) {
                bestDelta = res.bestDelta;
                bestA = Utils.multiplicativeModuloInverse(inverseA, modulus);
                bestB = (bestA * res.bestShift) % modulus;
            }
        }

        return new AffineShiftCracker.Result(bestA, bestB, bestDelta);
    }

    private double[] mapProbabilities(double[] probs, int coefficient) {
        int modulus = this.alphabet.length();

        double[] mappedProbs = new double[this.alphabet.length()];

        for (int i = 0; i < modulus; i++) {
            mappedProbs[(coefficient * i) % modulus] = probs[i];
        }

        return mappedProbs;
    }
}

