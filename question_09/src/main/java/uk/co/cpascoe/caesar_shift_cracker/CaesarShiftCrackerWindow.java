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

public class CaesarShiftCrackerWindow {

	private JFrame frame;
	private final Action encrypt = new EncryptAction();
	private final Action decrypt = new SwingAction_1();

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
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 512);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new MigLayout("", "[grow][grow]", "[][][grow][]"));

		JPanel panel_3 = new JPanel();
		panel.add(panel_3, "cell 0 0 2 1,grow");
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Test"}));
		panel_3.add(comboBox);

		JLabel lblPlaintext = new JLabel("Plaintext");
		lblPlaintext.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(lblPlaintext, "cell 0 1,alignx center");

		JLabel lblCiphertext = new JLabel("Ciphertext");
		lblCiphertext.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(lblCiphertext, "cell 1 1,alignx center");

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_1, "cell 0 2,grow");
		panel_1.setLayout(new BorderLayout(0, 0));

		JTextArea textArea = new JTextArea();
		panel_1.add(textArea);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_2, "cell 1 2,grow");
		panel_2.setLayout(new BorderLayout(0, 0));

		JTextArea textArea_1 = new JTextArea();
		panel_2.add(textArea_1, BorderLayout.CENTER);

		JButton btnNewButton = new JButton("Encrypt");
		btnNewButton.setAction(encrypt);
		panel.add(btnNewButton, "cell 0 3,grow");

		JButton btnNewButton_1 = new JButton("Decrypt");
		btnNewButton_1.setAction(decrypt);
		panel.add(btnNewButton_1, "cell 1 3,grow");
	}

	private class EncryptAction extends AbstractAction {
		public EncryptAction() {
			putValue(NAME, "Encrypt");
			putValue(SHORT_DESCRIPTION, "Encrypt the text");
		}
		public void actionPerformed(ActionEvent e) {
			System.out.println("Test");
		}
	}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "Decrypt");
			putValue(SHORT_DESCRIPTION, "Decrypt the text");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
