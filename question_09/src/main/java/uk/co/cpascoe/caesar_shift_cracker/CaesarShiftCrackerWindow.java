package uk.co.cpascoe.caesar_shift_cracker;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JLabel;
import java.awt.Font;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSplitPane;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;

public class CaesarShiftCrackerWindow {

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
					CaesarShiftCrackerWindow window = new CaesarShiftCrackerWindow();
					window.frmCaesarShift.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CaesarShiftCrackerWindow() {
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
		frmCaesarShift.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
		panel_1.add(this.txtrPlaintext);

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
		panel_2.add(this.txtrCiphertext, BorderLayout.CENTER);

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
				Alphabet alph = (Alphabet)CaesarShiftCrackerWindow.this.alphabetComboBox.getSelectedItem();
				CaesarShiftCrackerWindow.this.shiftSlider.setMaximum(alph.length() - 1);
				CaesarShiftCrackerWindow.this.shiftSlider.setValue(0);
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
			int shift = CaesarShiftCrackerWindow.this.shiftSlider.getValue();
			String plaintext = CaesarShiftCrackerWindow.this.txtrPlaintext.getText();
            Alphabet alph = (Alphabet)CaesarShiftCrackerWindow.this.alphabetComboBox.getSelectedItem();

            try {
                CaesarShiftCipher caesarShiftCipher = new CaesarShiftCipher(alph);
                String ciphertext = caesarShiftCipher.encrypt(plaintext, shift);

                CaesarShiftCrackerWindow.this.txtrCiphertext.setText(Utils.addSpaces(ciphertext, 5));
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
			int shift = CaesarShiftCrackerWindow.this.shiftSlider.getValue();
			String ciphertext = CaesarShiftCrackerWindow.this.txtrCiphertext.getText().replace(" ", "");
            Alphabet alph = (Alphabet)CaesarShiftCrackerWindow.this.alphabetComboBox.getSelectedItem();

            try {
                CaesarShiftCipher caesarShiftCipher = new CaesarShiftCipher(alph);
                String plaintext = caesarShiftCipher.decrypt(ciphertext, shift);

                CaesarShiftCrackerWindow.this.txtrPlaintext.setText(plaintext);
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
			String ciphertext = CaesarShiftCrackerWindow.this.txtrCiphertext.getText();
            Alphabet alph = (Alphabet)CaesarShiftCrackerWindow.this.alphabetComboBox.getSelectedItem();

            try {
                CaesarShiftCracker caesarShiftCracker = new CaesarShiftCracker(alph);
                int guessedShift = caesarShiftCracker.guessKey(ciphertext).bestShift;

                CaesarShiftCipher caesarShiftCipher = new CaesarShiftCipher(alph);
                String plaintext = caesarShiftCipher.decrypt(ciphertext, guessedShift);

                CaesarShiftCrackerWindow.this.shiftSlider.setValue(guessedShift);
                CaesarShiftCrackerWindow.this.txtrPlaintext.setText(plaintext);
            } catch (Exception ex) {
                System.out.println(ex);
            }
		}
	}
}
