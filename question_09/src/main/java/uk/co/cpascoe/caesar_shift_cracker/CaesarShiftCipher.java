package uk.co.cpascoe.caesar_shift_cracker;

public class CaesarShiftCipher {
    private Alphabet alphabet;

    public CaesarShiftCipher(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    public String encrypt(String plaintext, int shift) throws Exception {
        if (shift < 0 || shift >= this.alphabet.length()) {
            throw new Exception("'shift' is out of the valid range");
        }

        StringBuilder str = new StringBuilder();

        int len = this.alphabet.length();
        shift = shift % len;

        for (char c : this.alphabet.stripToAlphabet(plaintext).toCharArray()) {
            str.append(this.alphabet.getChar((this.alphabet.indexOf(c) + shift) % len));
        }

        return str.toString();
    }

    public String decrypt(String ciphertext, int shift) throws Exception {
        return this.encrypt(ciphertext, this.alphabet.length() - shift);
    }
}

