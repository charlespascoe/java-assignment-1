package uk.co.cpascoe.caesar_shift_cracker;


public class EnglishAlphabet extends Alphabet {
    public EnglishAlphabet() {
        super("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    @Override
    public String stripToAlphabet(String text) {
        return super.stripToAlphabet(text.toUpperCase());
    }

    @Override
    public double[] getExpectedProbabilities() {
        return new double[] {
            0.0884796573875803,
            0.01044967880085653,
            0.04899357601713062,
            0.031434689507494645,
            0.11623126338329764,
            0.02132762312633833,
            0.017473233404710922,
            0.04385438972162741,
            0.08428265524625268,
            0.0013704496788008566,
            0.004025695931477516,
            0.037430406852248396,
            0.03674518201284797,
            0.06997858672376873,
            0.06775160599571735,
            0.027323340471092077,
            0.0011991434689507495,
            0.06012847965738758,
            0.06852248394004283,
            0.09773019271948608,
            0.022441113490364026,
            0.0084796573875803,
            0.008907922912205567,
            0.002569593147751606,
            0.02226980728051392,
            0.0005995717344753747
        };
    }
}
