import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import java.awt.SystemColor;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Scanner;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;

public class Main {
	private static String directory;
	
	private JFrame frame;
	private JTextField tfDescription;
	private JTextField tfDeadline;
	private JTable table;

	/**
	 * Launch the application.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		Task.read();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
					
					try(Scanner sc=new Scanner(new FileInputStream("config.txt"))) {
						setDirectory(sc.nextLine());
					} catch(FileNotFoundException e) {
						new ConfigDialog().addWindowListener(new WindowAdapter() {
							public void windowClosed(WindowEvent e)
							  {
								if(Main.getDirectory()==null)
									System.exit(0);
							  }
						});
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static String getDirectory() {
		return directory;
	}
	
	public static void setDirectory(String dir) {
		directory = dir;
		createDirectory(dir);
	}
	
	public static void createDirectory(String dir) {
		new File(dir).mkdirs();
	}
	
	public static void removeDirectory(String dir) {
		try {
			Files.walk(Paths.get(dir)).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String copyFile(String sourceFile, String targetDir) {
		Path source = Paths.get(sourceFile);
		Path target = Paths.get(targetDir, "\\", source.getFileName().toString());
		
		try {
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			new InfoDialog("Copying file has failed");
			return source.toString();
		}
		
		return target.toString();
	}
	
	public static boolean isValidPath(String path) {
		if(!path.matches("^[a-zA-Z]:\\\\.*"))
			return false;
		
		try {
			Paths.get(path);
		} catch(InvalidPathException e) {
			return false;
		}
		return true;
	}
	
	public static boolean isValidDate(String date) {
		if(date.matches("\\d{4}-((0[1-9])|(1[0-2]))-((0[1-9])|([1-2][0-9])|(3[0-1]))") && (LocalDate.parse(date).isAfter(LocalDate.now()) || LocalDate.parse(date).isEqual(LocalDate.now())))
			return true;
		else
			return false;
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setBounds(100, 100, 350, 550);
		CardLayout cl=new CardLayout(0, 0);
		frame.getContentPane().setLayout(cl);
		
		JPanel tableCard = new JPanel();
		frame.getContentPane().add(tableCard, "name_45552194187000");
		tableCard.setLayout(new BorderLayout(0, 0));
		
		JPanel tablePanel = new JPanel();
		tableCard.add(tablePanel, BorderLayout.CENTER);
		tablePanel.setBorder(new EmptyBorder(10, 10, 0, 10));
		tablePanel.setLayout(new BorderLayout(0, 0));
		
		table = new JTable(new TaskTableModel(Task.getTaskExtent()));
		table.setBackground(SystemColor.control);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(60);
		
		table.setDefaultRenderer(Task.class, new TaskCellRenderer());
		table.setDefaultEditor(Task.class, new TaskCellEditor());
		
		table.setShowVerticalLines(false);
		table.setFillsViewportHeight(true);
		tablePanel.add(table, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		
		JPanel tablebtnPanel = new JPanel();
		tableCard.add(tablebtnPanel, BorderLayout.SOUTH);
		tablebtnPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		tablebtnPanel.setLayout(new BorderLayout(0, 0));
		
		JButton btnAddTask = new JButton("Add Task");
		btnAddTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.next(frame.getContentPane());
				
			}
		});
		btnAddTask.setBackground(SystemColor.controlHighlight);
		tablebtnPanel.add(btnAddTask);
		
		JPanel formCard = new JPanel();
		frame.getContentPane().add(formCard, "name_45607251487500");
		formCard.setLayout(new BorderLayout(0, 0));
		
		JPanel formPanel = new JPanel();
		formPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
		formCard.add(formPanel, BorderLayout.CENTER);
		formPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblSubject = new JLabel("Subject:");
		formPanel.add(lblSubject);
		
		JComboBox<String> cbSubject = new JComboBox(Task.getSubjectExtent().toArray());
		cbSubject.setBackground(SystemColor.controlHighlight);
		cbSubject.setEditable(true);
		cbSubject.setSelectedItem(null);
		formPanel.add(cbSubject);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		formPanel.add(verticalStrut);
		
		JLabel lblTaskType = new JLabel("Task type:");
		formPanel.add(lblTaskType);
		
		JComboBox<TaskType> cbTaskType = new JComboBox<>();
		for(TaskType tType : TaskType.values())
			cbTaskType.addItem(tType);
		cbTaskType.setBackground(SystemColor.controlHighlight);
		formPanel.add(cbTaskType);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		formPanel.add(verticalStrut_1);
		
		JLabel lblDescription = new JLabel("Description:");
		formPanel.add(lblDescription);
		
		tfDescription = new JTextField();
		tfDescription.setToolTipText("text or path to file");
		formPanel.add(tfDescription);
		tfDescription.setColumns(10);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		formPanel.add(verticalStrut_2);
		
		JLabel lblDeadline = new JLabel("Deadline:");
		formPanel.add(lblDeadline);
		
		tfDeadline = new JTextField();
		tfDeadline.setToolTipText("yyyy-mm-dd");
		formPanel.add(tfDeadline);
		tfDeadline.setColumns(10);
		
		JPanel btngpPanel = new JPanel();
		formPanel.add(btngpPanel);
		
		JLabel lblKeepOnDisk = new JLabel("Keep on disk:");
		btngpPanel.add(lblKeepOnDisk);
		
		JRadioButton rdbtnYes = new JRadioButton("Yes", true);
		btngpPanel.add(rdbtnYes);
		
		JRadioButton rdbtnNo = new JRadioButton("No");
		btngpPanel.add(rdbtnNo);
		
		ButtonGroup btngp=new ButtonGroup();
		btngp.add(rdbtnYes);
		btngp.add(rdbtnNo);
		
		
		JPanel formbtnPanel = new JPanel();
		formbtnPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		formCard.add(formbtnPanel, BorderLayout.SOUTH);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/*
				 * Form validation
				 */
				
				if(cbSubject.getSelectedItem()==null || tfDescription.getText().isEmpty() || tfDeadline.getText().isEmpty()) {
					new InfoDialog("All fields must be filled");
				} else if(Main.isValidDate(tfDeadline.getText())) {
					
					//Update combobox with list of subjects
					if(!Task.getSubjectExtent().contains((String) cbSubject.getSelectedItem()))
						cbSubject.addItem((String) cbSubject.getSelectedItem());
					
					//Create task object
					new Task((String) cbSubject.getSelectedItem(),(TaskType) cbTaskType.getSelectedItem(), tfDescription.getText(), tfDeadline.getText(), rdbtnYes.isSelected());
					
					//Refresh and go to Task table
					
					tableCard.revalidate();
					tableCard.repaint();
					cl.previous(frame.getContentPane());
					
					//Clear form
					cbSubject.setSelectedItem(null);
					cbTaskType.setSelectedIndex(0);
					tfDescription.setText("");
					tfDeadline.setText("");
					rdbtnYes.setSelected(true);
				} else {
					new InfoDialog("This is not a valid deadline date");
				}
				
			}
		});
		formbtnPanel.setLayout(new GridLayout(0, 2, 5, 0));
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Refresh and go to Task table
				tableCard.revalidate();
				tableCard.repaint();
				cl.previous(frame.getContentPane());
				
				//Clear form
				cbSubject.setSelectedItem(null);
				cbTaskType.setSelectedIndex(0);
				tfDescription.setText("");
				tfDeadline.setText("");
				rdbtnYes.setSelected(true);
				
			}
		});
		btnBack.setBackground(SystemColor.controlHighlight);
		formbtnPanel.add(btnBack);
		btnSave.setBackground(SystemColor.controlHighlight);
		formbtnPanel.add(btnSave);
	}
	
}
