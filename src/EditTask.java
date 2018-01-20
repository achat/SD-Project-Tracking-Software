import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

class EditTask implements ActionListener{
    private final MainWindow mainWin;
	private  JTable table_1;

    EditTask(final MainWindow textField){
        super();
        this.mainWin = textField;
        this.table_1 = mainWin.getTable();
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int selection = table_1.getSelectedRow();
		if (selection != -1) { 
			String value = table_1.getModel().getValueAt(selection, 1).toString();

			Task task = new Task();
			task.fillFromDb(selection);
			TaskWindow taskWin = new TaskWindow(mainWin);
			taskWin.editTask(task);
		} else {
			System.out.println("No task selected for edit!");
		}
	}
}





