package uk.co.cpascoe.caesar_shift_cracker;

public class EnglishWithPunctuationAlphabet extends Alphabet {
    public EnglishWithPunctuationAlphabet() {
        super("ABCDEFGHIJKLMNOPQRSTUVWXYZ_,'.-!?");
    }

    @Override
    public String stripToAlphabet(String text) {
        return super.stripToAlphabet(text.toUpperCase().replace(" ", "_"));
    }

    @Override
    public double[] getExpectedProbabilities() {
        return new double[this.length()];
    }

    @Override
    public String toString() {
        return String.format("English with Punctuation (%s)", super.toString());
    }
}
