package cipher_cracker;

public class AffineShiftCipher {
    private Alphabet alphabet;

    public AffineShiftCipher(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    public String encrypt(String plaintext, int a, int b) throws InvalidKeyException {
        int modulus = this.alphabet.length();

        a = Utils.absoluteModulo(a, modulus);
        b = Utils.absoluteModulo(b, modulus);

        if (Utils.gcd(a, modulus) != 1) {
            throw new InvalidKeyException("a", a, "expected a number coprime to " + Integer.toString(this.alphabet.length()));
        }

        StringBuilder str = new StringBuilder();

        for (char c : this.alphabet.stripToAlphabet(plaintext).toCharArray()) {
            str.append(this.alphabet.getChar((a * this.alphabet.indexOf(c) + b) % modulus));
        }

        return str.toString();
    }

    public String decrypt(String ciphertext, int a, int b) throws InvalidKeyException {
        int modulus = this.alphabet.length();
        int inverseA = Utils.multiplicativeModuloInverse(a, modulus);
        int minusInverseAB = (modulus - ((inverseA * b) % modulus)) % modulus;
        return this.encrypt(ciphertext, inverseA, minusInverseAB);
    }
}

