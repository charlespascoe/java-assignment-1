package uk.co.cpascoe.caesar_shift_cracker;


public abstract class Alphabet {
    private String alphabet;

    /**
     * Class constructor, with the specifed alphabet
     *
     * @param alphabet the string of distinct characters that represents this alphabet
     */
    protected Alphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    /**
     * Gets the number of characters of this alphabet
     */
    public int length() {
        return this.alphabet.length();
    }

    /**
     * Gets the index of the specified charater within this alphabet, or -1 if not present
     *
     * @param c the character to get the index of
     */
    public int indexOf(char c) {
        return this.alphabet.indexOf((int)c);
    }

    /**
     * Gets the character at the specified index
     *
     * @param index the index of the character in this alphabet
     */
    public char getChar(int index) {
        if (index < 0 || index >= this.alphabet.length()) {
            throw new Exception("Array index out of bounds!");
        }

        return this.alphabet[index];
    }

    /**
     *  Removes all characters not in this alphabet
     *
     *  @param text the input text
     */
    public String stripToAlphabet(String text) {
        StringBuilder str = new StringBuilder();

        for (char c : text) {
            if (this.alphabet.indexOf((int)c) >= 0) {
                str.append(c);
            }
        }

        return str.toString();
    }

    /**
     * Gets the expected probabilities of each character of this alphabet
     */
    public abstract double[] getExpectedProbabilities();

    /**
     * Computes the probabilities of each character occurring in the provided text
     *
     * @param text the input text
     */
    public double[] computeProbabilities(String text) {
        text = this.stripToAlphabet(text);

        //TODO: Check that text.length is greater than 0

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
