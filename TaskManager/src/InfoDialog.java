import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class InfoDialog extends JDialog {

	/**
	 * Create the dialog.
	 */
	public InfoDialog(String message) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Info");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setBounds(100, 100, 350, 200);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 100, 10, 100));
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnOk.setBackground(SystemColor.controlHighlight);
		panel.add(btnOk, BorderLayout.NORTH);
		
		JLabel lblMessage = new JLabel(message);
		lblMessage.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblMessage, BorderLayout.CENTER);
		
		setVisible(true);
	}

}
