import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

class AddTask implements ActionListener{
    private final MainWindow mainWin_;

    AddTask(final MainWindow mainWin){
        super();
        this.mainWin_ = mainWin;
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		TaskWindow taskWin = new TaskWindow(mainWin_);
		taskWin.addNewTask();
	}
}