import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;


public class TaskTableModel extends AbstractTableModel {
	private List<Task> data;
	
	public TaskTableModel(List<Task> data) {
		this.data = data;
//		Collections.sort(this.data, (t1, t2)->t1.importanceLevel-t2.importanceLevel);
	}
	
	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public int getRowCount() {
		if(data==null)
			return 0;
		else
			return data.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		if(data==null)
			return null;
		else
			return (Task) data.toArray()[row];
	}
	
	@Override
	public Class<?> getColumnClass(int column) {
		return Task.class;
	}
	
	@Override
	public String getColumnName(int cloumn) {
		return "Task";
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}

}
