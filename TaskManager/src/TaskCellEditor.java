import java.awt.Component;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

public class TaskCellEditor extends AbstractCellEditor implements TableCellEditor {
	private TaskCellComponent panel;
	
	
	public TaskCellEditor() {
		panel = new TaskCellComponent();
	}
	
	@Override
	public Object getCellEditorValue() {
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		Task task = (Task) value;
		panel.updateData(task, isSelected, table);
		
		return panel;
	}

}
