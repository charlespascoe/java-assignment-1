package cipher_cracker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class AffineShiftWindow {

	private JFrame frmAffineShift;
	private final Action encrypt = new EncryptAction();
	private final Action decrypt = new DecryptAction();
	private final Action guessKey = new GuessKeyAction();
	private JComboBox<Alphabet> alphabetComboBox;
	private JTextArea txtrPlaintext;
	private JTextArea txtrCiphertext;
	private JComboBox<Integer> coefficientComboBox;
	private JComboBox<Integer> shiftComboBox;

	/**
	 * Create the application.
	 */
	public AffineShiftWindow() {
		initialize();
	}

	public void show() {
		this.frmAffineShift.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAffineShift = new JFrame();
		frmAffineShift.setResizable(false);
		frmAffineShift.setTitle("Affine Shift");
		frmAffineShift.setBounds(100, 100, 685, 450);
		frmAffineShift.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		frmAffineShift.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setResizeWeight(0.5);
		splitPane.setBounds(10, 36, 659, 343);
		panel.add(splitPane);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(null);
		splitPane.setLeftComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 5));

		JLabel lblPlaintext = new JLabel("Plaintext");
		lblPlaintext.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblPlaintext, BorderLayout.NORTH);

		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.setAction(encrypt);
		panel_3.add(btnEncrypt, BorderLayout.SOUTH);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_3.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		this.txtrPlaintext = new JTextArea();
		txtrPlaintext.setLineWrap(true);
		txtrPlaintext.setWrapStyleWord(true);
		JScrollPane plaintextScrollPane = new JScrollPane(this.txtrPlaintext);
		plaintextScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_1.add(plaintextScrollPane);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(null);
		splitPane.setRightComponent(panel_4);
		panel_4.setLayout(new BorderLayout(0, 5));

		JLabel lblCiphertext = new JLabel("Ciphertext");
		lblCiphertext.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblCiphertext, BorderLayout.NORTH);

		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.setAction(decrypt);
		panel_4.add(btnDecrypt, BorderLayout.SOUTH);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_4.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		this.txtrCiphertext = new JTextArea();
		txtrCiphertext.setWrapStyleWord(true);
		txtrCiphertext.setLineWrap(true);
		JScrollPane ciphertextScrollPane = new JScrollPane(this.txtrCiphertext);
		ciphertextScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_2.add(ciphertextScrollPane);

		JLabel lblAlphabet = new JLabel("Alphabet");
		lblAlphabet.setBounds(10, 11, 46, 14);
		panel.add(lblAlphabet);

		this.coefficientComboBox = new JComboBox<Integer>();
		this.coefficientComboBox.setBounds(253, 390, 142, 20);
		panel.add(coefficientComboBox);

		this.shiftComboBox = new JComboBox<Integer>();
		this.shiftComboBox.setBounds(427, 390, 142, 20);
		panel.add(shiftComboBox);

		this.alphabetComboBox = new JComboBox<Alphabet>();
		this.alphabetComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Alphabet alph = (Alphabet)AffineShiftWindow.this.alphabetComboBox.getSelectedItem();
				AffineShiftWindow.this.coefficientComboBox.setModel(new DefaultComboBoxModel<Integer>(Utils.getCoprimes(alph.length())));
				AffineShiftWindow.this.shiftComboBox.setModel(new DefaultComboBoxModel<Integer>(Utils.getIntegers(alph.length())));
			}
		});
		this.alphabetComboBox.setBounds(66, 8, 603, 20);
		this.alphabetComboBox.addItem(new EnglishAlphabet());
		this.alphabetComboBox.addItem(new EnglishWithPunctuationAlphabet());
		panel.add(this.alphabetComboBox);

		JButton btnGuessKey = new JButton("Guess Key");
		btnGuessKey.setAction(guessKey);
		btnGuessKey.setBounds(579, 389, 90, 23);
		panel.add(btnGuessKey);



		JLabel lblA = new JLabel("A");
		lblA.setBounds(231, 393, 12, 14);
		panel.add(lblA);

		JLabel lblB = new JLabel("B");
		lblB.setBounds(405, 393, 12, 14);
		panel.add(lblB);
	}

	private class EncryptAction extends AbstractAction {
		public EncryptAction() {
			putValue(NAME, "Encrypt");
			putValue(SHORT_DESCRIPTION, "Encrypt the text");
		}
		public void actionPerformed(ActionEvent e) {
			int coefficient = (int)AffineShiftWindow.this.coefficientComboBox.getSelectedItem();
			int shift = (int)AffineShiftWindow.this.shiftComboBox.getSelectedItem();
			String plaintext = AffineShiftWindow.this.txtrPlaintext.getText();
            Alphabet alph = (Alphabet)AffineShiftWindow.this.alphabetComboBox.getSelectedItem();

		    try {
                AffineShiftCipher affineShiftCipher = new AffineShiftCipher(alph);
                String ciphertext = affineShiftCipher.encrypt(plaintext, coefficient, shift);

                AffineShiftWindow.this.txtrCiphertext.setText(Utils.addSpaces(ciphertext, 5));
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
	}
	private class DecryptAction extends AbstractAction {
		public DecryptAction() {
			putValue(NAME, "Decrypt");
			putValue(SHORT_DESCRIPTION, "Decrypt the text");
		}
		public void actionPerformed(ActionEvent e) {
			int coefficient = (int)AffineShiftWindow.this.coefficientComboBox.getSelectedItem();
			int shift = (int)AffineShiftWindow.this.shiftComboBox.getSelectedItem();
			String ciphertext = AffineShiftWindow.this.txtrCiphertext.getText().replace(" ", "");
            Alphabet alph = (Alphabet)AffineShiftWindow.this.alphabetComboBox.getSelectedItem();

		    try {
                AffineShiftCipher affineShiftCipher = new AffineShiftCipher(alph);
                String plaintext = affineShiftCipher.decrypt(ciphertext, coefficient, shift);

                AffineShiftWindow.this.txtrPlaintext.setText(plaintext);
            } catch (Exception ex) {
                System.out.println(ex);
            }
		}
	}
	private class GuessKeyAction extends AbstractAction {
		public GuessKeyAction() {
			putValue(NAME, "Guess Key");
			putValue(SHORT_DESCRIPTION, "Guess the shift of the provided ciphertext");
		}
		public void actionPerformed(ActionEvent e) {
			String ciphertext = AffineShiftWindow.this.txtrCiphertext.getText().replace(" ", "");
            Alphabet alph = (Alphabet)AffineShiftWindow.this.alphabetComboBox.getSelectedItem();

            try {
                AffineShiftCracker affineShiftCracker = new AffineShiftCracker(alph);
                AffineShiftCracker.Result result = affineShiftCracker.guessKey(ciphertext);

                AffineShiftCipher affineShiftCipher = new AffineShiftCipher(alph);
                String plaintext = affineShiftCipher.decrypt(ciphertext, result.bestA, result.bestB);

			    AffineShiftWindow.this.coefficientComboBox.setSelectedItem(result.bestA);
                AffineShiftWindow.this.shiftComboBox.setSelectedItem(result.bestB);
                AffineShiftWindow.this.txtrPlaintext.setText(plaintext);
            } catch (Exception ex) {
                System.out.println(ex);
            }
		}
	}
}
