package uk.co.cpascoe.caesar_shift_cracker;

public class AffineShiftCipher {
    private Alphabet alphabet;

    public AffineShiftCipher(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    public String encrypt(String plaintext, int a, int b) throws Exception {
        if (a < 0 || a >= this.alphabet.length()) {
            throw new Exception("'a' out of valid range" + Integer.toString(a));
        }

        if (b < 0 || b >= this.alphabet.length()) {
            throw new Exception("'b' out of valid range: " + Integer.toString(b));
        }

        if (Utils.gcd(a, this.alphabet.length()) != 1) {
            throw new Exception("'a' must be coprime to the length of the alphabet: " + Integer.toString(a));
        }

        StringBuilder str = new StringBuilder();

        int modulus = this.alphabet.length();

        for (char c : this.alphabet.stripToAlphabet(plaintext).toCharArray()) {
            str.append(this.alphabet.getChar((a * this.alphabet.indexOf(c) + b) % modulus));
        }

        return str.toString();
    }

    public String decrypt(String ciphertext, int a, int b) throws Exception {
        int modulus = this.alphabet.length();
        int inverseA = Utils.multiplicativeModuloInverse(a, modulus);
        int minusInverseAB = (modulus - ((inverseA * b) % modulus)) % modulus;
        return this.encrypt(ciphertext, inverseA, minusInverseAB);
    }
}

