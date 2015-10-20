package uk.co.cpascoe.caesar_shift_cracker;


public abstract class Alphabet {
    private String alphabet;
    protected Alphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public int length() {
        return this.alphabet.length();
    }

    public int indexOf(char c) {
        return this.alphabet.indexOf((int)c);
    }

    public char getChar(int x) {
        if (x < 0 || x >= this.alphabet.length()) {
            throw new Exception("Array index out of bounds!");
        }

        return this.alphabet[x];
    }

    public String stripToAlphabet(String text) {
        StringBuilder str = new StringBuilder();

        for (char c : text) {
            if (this.alphabet.indexOf((int)c) >= 0) {
                str.append(c);
            }
        }

        return str.toString();
    }

    public abstract double[] getExpectedProbabilities();

    public double[] computeProbabilities(String text) {
        text = this.stripToAlphabet(text);

        int[] freqs = new int[this.length()];

        for (char c : text) {
            freqs[this.indexOf(c)]++;
        }

        double[] probs = new double[freqs.length];

        for (int i = 0; i < freqs.length; i++) {
            probs[i] = (double)freqs[i] / text.length;
        }

        return probs;
    }
}
