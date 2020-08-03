import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ConfigDialog extends JDialog {
	private JTextField textField;

	/**
	 * Create the dialog.
	 */
	public ConfigDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Configuration");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setBounds(100, 100, 350, 175);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 10, 0));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Main.isValidPath(textField.getText())) {
					Main.setDirectory(textField.getText());
					try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("config.txt", false)))) {
						out.write(Main.getDirectory());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					dispose();
				} else {
					new InfoDialog("This is not a valid directory!");
				}
				
			}
		});
		btnSave.setBackground(SystemColor.controlHighlight);
		panel.add(btnSave);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(20, 50, 20, 50));
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblSaveDirectoryPath = new JLabel("Save directory path:");
		lblSaveDirectoryPath.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblSaveDirectoryPath);
		
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);

		setVisible(true);
	}
	
}
