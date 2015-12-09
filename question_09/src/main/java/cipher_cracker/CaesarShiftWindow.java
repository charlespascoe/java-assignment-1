package cipher_cracker;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

public class CaesarShiftWindow {

	private JFrame frmCaesarShift;
	private final Action encrypt = new EncryptAction();
	private final Action decrypt = new DecryptAction();
	private final Action guessKey = new GuessKeyAction();
	private JComboBox<Alphabet> alphabetComboBox;
	private JSlider shiftSlider;
	private JTextArea txtrPlaintext;
	private JTextArea txtrCiphertext;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					CaesarShiftWindow window = new CaesarShiftWindow();
					AffineShiftWindow aw = new AffineShiftWindow();
					window.frmCaesarShift.setVisible(true);
                    aw.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CaesarShiftWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCaesarShift = new JFrame();
		frmCaesarShift.setResizable(false);
		frmCaesarShift.setTitle("Caesar Shift");
		frmCaesarShift.setBounds(100, 100, 685, 481);
		frmCaesarShift.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		frmCaesarShift.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JSplitPane splitPane = new JSplitPane();
        splitPane.setBorder(null);
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

		this.shiftSlider = new JSlider();
		this.shiftSlider.setMajorTickSpacing(1);
		this.shiftSlider.setPaintLabels(true);
		this.shiftSlider.setPaintTicks(true);
		this.shiftSlider.setValue(0);
		this.shiftSlider.setMaximum(25);
		this.shiftSlider.setSnapToTicks(true);
		this.shiftSlider.setBounds(51, 390, 518, 52);
		panel.add(this.shiftSlider);

		this.alphabetComboBox = new JComboBox<Alphabet>();
		this.alphabetComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Alphabet alph = (Alphabet)CaesarShiftWindow.this.alphabetComboBox.getSelectedItem();
				CaesarShiftWindow.this.shiftSlider.setMaximum(alph.length() - 1);
				CaesarShiftWindow.this.shiftSlider.setValue(0);
			}
		});
		this.alphabetComboBox.setBounds(66, 8, 603, 20);
		this.alphabetComboBox.addItem(new EnglishAlphabet());
		this.alphabetComboBox.addItem(new EnglishWithPunctuationAlphabet());
		panel.add(this.alphabetComboBox);

		JLabel lblShift = new JLabel("Shift");
		lblShift.setBounds(10, 390, 31, 52);
		panel.add(lblShift);

		JButton btnGuessKey = new JButton("Guess Key");
		btnGuessKey.setAction(guessKey);
		btnGuessKey.setBounds(579, 405, 90, 23);
		panel.add(btnGuessKey);
	}

	private class EncryptAction extends AbstractAction {
		public EncryptAction() {
			putValue(NAME, "Encrypt");
			putValue(SHORT_DESCRIPTION, "Encrypt the text");
		}
		public void actionPerformed(ActionEvent e) {
			int shift = CaesarShiftWindow.this.shiftSlider.getValue();
			String plaintext = CaesarShiftWindow.this.txtrPlaintext.getText();
            Alphabet alph = (Alphabet)CaesarShiftWindow.this.alphabetComboBox.getSelectedItem();

            try {
                CaesarShiftCipher caesarShiftCipher = new CaesarShiftCipher(alph);
                String ciphertext = caesarShiftCipher.encrypt(plaintext, shift);

                CaesarShiftWindow.this.txtrCiphertext.setText(Utils.addSpaces(ciphertext, 5));
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
			int shift = CaesarShiftWindow.this.shiftSlider.getValue();
			String ciphertext = CaesarShiftWindow.this.txtrCiphertext.getText().replace(" ", "");
            Alphabet alph = (Alphabet)CaesarShiftWindow.this.alphabetComboBox.getSelectedItem();

            try {
                CaesarShiftCipher caesarShiftCipher = new CaesarShiftCipher(alph);
                String plaintext = caesarShiftCipher.decrypt(ciphertext, shift);

                CaesarShiftWindow.this.txtrPlaintext.setText(plaintext);
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
			String ciphertext = CaesarShiftWindow.this.txtrCiphertext.getText().replace(" ", "");
            Alphabet alph = (Alphabet)CaesarShiftWindow.this.alphabetComboBox.getSelectedItem();

            try {
                CaesarShiftCracker caesarShiftCracker = new CaesarShiftCracker(alph);
                int guessedShift = caesarShiftCracker.guessKey(ciphertext).bestShift;

                CaesarShiftCipher caesarShiftCipher = new CaesarShiftCipher(alph);
                String plaintext = caesarShiftCipher.decrypt(ciphertext, guessedShift);

                CaesarShiftWindow.this.shiftSlider.setValue(guessedShift);
                CaesarShiftWindow.this.txtrPlaintext.setText(plaintext);
            } catch (Exception ex) {
                System.out.println(ex);
            }
		}
	}
}
