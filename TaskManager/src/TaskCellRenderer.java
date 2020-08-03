import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TaskCellRenderer implements TableCellRenderer {
	private TaskCellComponent panel;
	
	public TaskCellRenderer() {
		panel=new TaskCellComponent();
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Task task = (Task) value;
		panel.updateData(task, isSelected, table);
		
		return panel;
	}

}
