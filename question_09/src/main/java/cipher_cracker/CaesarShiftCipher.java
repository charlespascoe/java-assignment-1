package cipher_cracker;

public class CaesarShiftCipher {
    private Alphabet alphabet;

    public CaesarShiftCipher(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    public String encrypt(String plaintext, int shift) {
        StringBuilder str = new StringBuilder();

        int len = this.alphabet.length();

        shift = Utils.absoluteModulo(shift, len);

        for (char c : this.alphabet.stripToAlphabet(plaintext).toCharArray()) {
            str.append(this.alphabet.getChar((this.alphabet.indexOf(c) + shift) % len));
        }

        return str.toString();
    }

    public String decrypt(String ciphertext, int shift) {
        return this.encrypt(ciphertext, this.alphabet.length() - shift);
    }
}

