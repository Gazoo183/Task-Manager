import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum TaskType {EXERCISE, ESSAY, PROGRAM, PROJECT}
enum DescriptionType {TEXT, FILE}

public class Task implements Serializable {
	public static Map<Class, List> extents = new HashMap<>();
	
	private String subject;
	private TaskType taskType;
	private String description;
	private LocalDate deadline;
	private boolean keep;
	private String directory;
	private int importanceLevel;
	
	public Task(String subject, TaskType taskType, String description, String deadline, boolean keep) {
		this.subject = subject;
		this.taskType = taskType;
		this.description = description;
		this.deadline = LocalDate.parse(deadline);
		this.keep = keep;
		
		//Save on disk
		if(keep) {
			directory = Main.getDirectory()+"\\"+subject+"\\"+LocalDate.now();
			Main.createDirectory(directory);
			if(this.getDescriptionType()==DescriptionType.FILE)
				this.description = Main.copyFile(description, directory);
			else {
				try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(directory+"\\"+"description.txt", false)))) {
					out.write(description);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		}
		
		//Add to extent
		addTask(this);
	}
	
	/*
	 * Extent write/read
	 */
	
	public static void write() {
		try(ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("extentFile.txt")))) {
			out.writeObject(extents);
		} catch(Exception e) {
			new InfoDialog("Saving extent to file has failed");
		}
	}
	
	public static void read() {
		try(ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("extentFile.txt")))) {
			extents = (Map<Class, List>) in.readObject();
		} catch (Exception e) {
			setExtents();
			write();
		}
		
	}
	
	/*
	 * Extent getters/setters
	 */
	
	public static List<String> getSubjectExtent() {
		return (ArrayList<String>) extents.get(String.class);
	}
	
	public static List<Task> getTaskExtent(){
		return (ArrayList<Task>) extents.get(Task.class);
	}
	
	public static void setExtents() {
		extents.put(String.class, new ArrayList<String>());
		extents.put(Task.class, new ArrayList<Task>());
		
	}
	
	/*
	 * Extent add to/remove from
	 */
	
	private static void addSubject(String subject) {
		if(!getSubjectExtent().contains(subject))
			getSubjectExtent().add(subject);
		write();
		
	}
	
	public static void addTask(Task task) {
		if(!getTaskExtent().contains(task)) {
			getTaskExtent().add(task);
			addSubject(task.subject);
		}
		
	}
	
	public static void removeSubject(String subject) {
		getSubjectExtent().remove(subject);
		write();
		
	}
	
	public static void removeTask(Task task) {
		getTaskExtent().remove(task);
		write();
		
	}
	
	/*
	 * Attribute getters
	 */
	
		public boolean getKeep() {
			return keep;
		}
		
		public String getDirectory() {
			return directory;
		}
	
		public String getDescription() {
			return description;
		}
		
		public String getSubject() {
			return subject;
		}
		
		public TaskType getTaskType() {
			return taskType;
		}
		
		public LocalDate getDeadline() {
			return deadline;
		}
	/*
	 * Other
	 */
	
	public DescriptionType getDescriptionType() {
		if(Main.isValidPath(description))
			return DescriptionType.FILE;
		else
			return DescriptionType.TEXT;
	}
	
}
