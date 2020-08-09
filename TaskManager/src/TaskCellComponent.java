import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class TaskCellComponent extends JPanel{
	private Task task;
	private JTable table;
	
	private JPanel topPanel;
	private JPanel lblPanel;
	private JPanel btnPanel;
	private JPanel btnsPanel;
	
	private JLabel lblSubject;
	private JLabel lblType;
	private JLabel lblDeadline;
	
	private JButton delete;
	private JButton postpone;
	private JButton view;
	private JButton handIn;
	
	public TaskCellComponent() {
		setLayout(new BorderLayout(0,0));
		
		topPanel = new JPanel(new BorderLayout(0,0));
		lblPanel = new JPanel(new GridLayout(0, 3, 0, 0));
		btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
		btnPanel.setBorder(new EmptyBorder(0,0,0,3));
		btnsPanel = new JPanel(new GridLayout(1,0,5,0));
		btnsPanel.setBorder(new EmptyBorder(0,2,2,2));
		
		lblSubject = new JLabel();
		lblSubject.setHorizontalAlignment(SwingConstants.CENTER);
		lblType = new JLabel();
		lblType.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeadline = new JLabel();
		lblDeadline.setHorizontalAlignment(SwingConstants.CENTER);
		
		delete = new JButton("X");
		delete.setBackground(SystemColor.controlHighlight);
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(task.getKeep())
					Main.removeDirectory(task.getDirectory());
				Task.removeTask(task);
				
			}
		});
		postpone = new JButton("Postpone");
		postpone.setBackground(SystemColor.controlHighlight);
		postpone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//decrease importance level
			}
		});
		view = new JButton("View");
		view.setBackground(SystemColor.controlHighlight);
		view.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(task.getDescriptionType()==DescriptionType.FILE) {
					try {
						Desktop.getDesktop().open(new File(task.getDirectory()));
					} catch (IOException e) {
						e.printStackTrace();
						new InfoDialog("Application could not open the file");
					}
				} else {
					new InfoDialog(task.getDescription());
				}
				
			}
		});
		handIn = new JButton("Hand in");
		handIn.setBackground(SystemColor.controlHighlight);
		handIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(task.getKeep()) {
					String solution = JOptionPane.showInputDialog(null, "Enter solution's path", "Hand in", JOptionPane.QUESTION_MESSAGE);
					if(solution!=null && Main.isValidPath(solution)) {
						Main.copyFile(solution, task.getDirectory());
						Task.removeTask(task);
					} else if(solution!=null) {
						new InfoDialog("This is not a valid path");
					}
				} else {
					Task.removeTask(task);
				}
				
			}
		});
		
		btnPanel.add(delete);
		btnsPanel.add(postpone);
		btnsPanel.add(view);
		btnsPanel.add(handIn);
		
		lblPanel.add(lblSubject);
		lblPanel.add(lblType);
		lblPanel.add(lblDeadline);
		
		topPanel.add(lblPanel, BorderLayout.CENTER);
		topPanel.add(btnPanel, BorderLayout.EAST);
		
		add(topPanel, BorderLayout.CENTER);
		add(btnsPanel, BorderLayout.SOUTH);
	}
	
	public void updateData(Task task, boolean isSelected, JTable table) {
		this.table=table;
		this.task=task;
		
		this.lblSubject.setText(task.getSubject());
		this.lblType.setText(task.getTaskType().toString());
		this.lblDeadline.setText(task.getDeadline().toString());
		
		if(isSelected) {
			btnsPanel.setVisible(true);
		} else {
			btnsPanel.setVisible(false);
		}
	}
}
