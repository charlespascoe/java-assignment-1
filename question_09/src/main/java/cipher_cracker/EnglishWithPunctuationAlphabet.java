package cipher_cracker;

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
        return new double[] {
            0.07382262559851355,
            0.008718645036804116,
            0.04087758164796684,
            0.02622739941399271,
            0.0969770599585507,
            0.01779461159151004,
            0.014578717930393769,
            0.03658972343314514,
            0.07032087472307583,
            0.0011434288572857857,
            0.0033588222682769957,
            0.031229900664618022,
            0.03065818623597513,
            0.05838633602515544,
            0.05652826413206603,
            0.022797112842135352,
            0.0010005002501250625,
            0.05016794111341385,
            0.05717144286428929,
            0.0815407703851926,
            0.018723647538054743,
            0.0070749660544557995,
            0.007432287572357608,
            0.002143929107410848,
            0.018580718930894018,
            5.002501250625312E-4,
            0.1470735367683842,
            0.009361823769027371,
            5.717144286428928E-4,
            0.0080040020010005,
            6.431787322232545E-4,
            0.0,
            0.0
        };
    }

    @Override
    public String toString() {
        return String.format("English with Punctuation (%s)", super.toString());
    }
}
