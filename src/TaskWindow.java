import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Color;

public class TaskWindow extends JFrame {

	private JTextField txtFieldTaskID;
	private JTextField txtFieldDescription;
	private JTextField txtFieldTitle;
	private JTextField txtFieldDate;
	private JComboBox comboBoxState;
	private Connection connection = null;
    PreparedStatement pst = null;
    private String timeStamp_;
    private JComboBox comboBoxAssignee;
    
    private int prevId_; 
	private String prevTitle_;
	private String prevDescription_;
	private String prevDateCreated_;
	private String prevAssignee_;
	private String prevState_;
    private Task task_;
    
    private static void editTask(Task task) {
    	TaskWindow frame = new TaskWindow(task);
    	frame.setVisible(true);
    }
    
    private static void addNewTask() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskWindow frame = new TaskWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
    }
    
    public static void display(Task task) {
		if (task != null) {
			editTask(task);
		} else {
			addNewTask();
		}
	}
	
	private int getUserId(String assignee) {
		int id = 0;
		String sql = "Select id from users where username = ?";

	    try {
	    	pst = connection.prepareStatement(sql);
	        pst.setString(1, assignee);
	        ResultSet rs = pst.executeQuery();
	        while (rs.next()) {
	        	int s = rs.getInt("id");
	        	System.out.println(s);
	        }
	    } catch (Exception e) {
	       JOptionPane.showConfirmDialog(null, e);
	    }
		return id;
	}
	
	public void appendUsersToCombo() {
		String sql = "Select username from users";

	    try {
	    	pst = connection.prepareStatement(sql);
	        ResultSet rs = pst.executeQuery();
	        while (rs.next()) {
	        	String name = rs.getString("username");
	        	comboBoxAssignee.addItem(name);
	        }
	    } catch (Exception e) {
	       JOptionPane.showConfirmDialog(null, e);
	    }
	}
	
	private void createWinCore() {
		connection = JavaConnector.ConnectDb();		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 692, 495);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		timeStamp_ = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
		
		getContentPane().setLayout(null);
		
		JLabel labelTaskID = new JLabel("Task ID");
		labelTaskID.setBounds(40, 53, 70, 15);
		getContentPane().add(labelTaskID);
		
		JLabel labelAssignee = new JLabel("Assignee");
		labelAssignee.setBounds(40, 158, 70, 35);
		getContentPane().add(labelAssignee);
		
		JLabel labelTitle = new JLabel("Title");
		labelTitle.setBounds(40, 89, 70, 15);
		getContentPane().add(labelTitle);
		
		JLabel labelDescription = new JLabel("Description");
		labelDescription.setBounds(37, 276, 107, 15);
		getContentPane().add(labelDescription);
		
		txtFieldTaskID = new JTextField();
		txtFieldTaskID.setEnabled(false);
		txtFieldTaskID.setEditable(false);
		txtFieldTaskID.setBounds(160, 51, 165, 24);
		getContentPane().add(txtFieldTaskID);
		txtFieldTaskID.setColumns(10);
		txtFieldTaskID.setText(Integer.toString(TaskListWindow.taskNum_ + 1));
		
		txtFieldDescription = new JTextField();
		txtFieldDescription.setColumns(10);
		txtFieldDescription.setBounds(157, 274, 306, 146);
		getContentPane().add(txtFieldDescription);
		
		comboBoxAssignee = new JComboBox();
		
		comboBoxAssignee.setModel(new DefaultComboBoxModel(new String[] {"Unassigned"}));
		comboBoxAssignee.setBounds(160, 163, 165, 30);
		getContentPane().add(comboBoxAssignee);
		
		txtFieldTitle = new JTextField();
		txtFieldTitle.setColumns(10);
		txtFieldTitle.setBounds(160, 87, 391, 24);
		getContentPane().add(txtFieldTitle);

		JLabel labelState = new JLabel("State");
		labelState.setBounds(40, 129, 70, 15);
		getContentPane().add(labelState);
		
		comboBoxState = new JComboBox();
		comboBoxState.setModel(new DefaultComboBoxModel(new String[] {"New", "Under Dev", "Completed", "Closed"}));
		comboBoxState.setBounds(160, 121, 165, 30);
		getContentPane().add(comboBoxState);
		
		JLabel labelDate = new JLabel("Date");
		labelDate.setBounds(40, 210, 70, 35);
		getContentPane().add(labelDate);
		
		txtFieldDate = new JTextField();
		txtFieldDate.setEnabled(true);
		txtFieldDate.setEditable(false);
		txtFieldDate.setColumns(10);
		txtFieldDate.setBounds(160, 218, 165, 24);
		txtFieldDate.setText(timeStamp_);
		getContentPane().add(txtFieldDate);

		appendUsersToCombo();
	}

	public TaskWindow() {
		createWinCore();
		
		JButton buttonDone = new JButton("Done");
		buttonDone.setBounds(403, 432, 117, 25);
		getContentPane().add(buttonDone);
		
		buttonDone.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {	
		String sql = "Insert into tasks (title, description, state, assignee, date_created) values (?, ?, ?, ?, ?)";

		    try {
		    	PreparedStatement pst = null;
		    	pst = connection.prepareStatement(sql);
		    	Task task = new Task();
		        task.setTitle(txtFieldTitle.getText());
		        task.setDescription(txtFieldDescription.getText());
		        task.setState(String.valueOf(comboBoxState.getSelectedItem()));
		        task.setAssignee(String.valueOf(comboBoxAssignee.getSelectedItem()));
		        
		        if (task.getTitle().equals("")) {
		        	task.setTitle(null);
		        }
		        task.setDateCreated(new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));
		     		        
		        pst.setString(1, task.getTitle());
		        pst.setString(2, task.getDescription());
		        pst.setString(3, task.getState());
		        pst.setString(4, task.getAssignee());
		        pst.setString(5, task.getDateCreated());
		        
		        if (!pst.execute()) {
		        	TaskListWindow.refreshTable();
		        }
		    } catch (Exception e) {
		       JOptionPane.showConfirmDialog(null, e);
		    }
			dispose();
			}
		});
	}
	
	public void comboSetSelectedByValue(JComboBox combo, String state) {
        for (int i = 0; i < combo.getItemCount(); i++)
        {
        	String lala = combo.getItemAt(i).toString();
        	if (lala.equals(state)) {
        		combo.setSelectedIndex(i);
        	}
        }
	}
	
	private void storePrevVals(Task task) {
		prevId_          = task.getId();
		prevTitle_       = task.getTitle();
		prevDescription_ = task.getDescription();
		prevDateCreated_ = task.getDateCreated();
		prevAssignee_    = task.getAssignee();
		prevState_       = task.getState(); 
	}
	
	private boolean needsUpdate() {
		if ((txtFieldTitle.getText() != prevTitle_) ||
				(txtFieldDescription.getText() != prevDescription_) ||
				(txtFieldDate.getText() != prevDateCreated_) ||
				(prevAssignee_ != comboBoxAssignee.getSelectedItem().toString()) ||
				(prevState_ != comboBoxState.getSelectedItem().toString()))
			{
				return true;
			}
		return false;
				
	}
	
	public TaskWindow(Task task) {
		createWinCore();
		task_ = task;
		
		txtFieldTaskID.setText(Integer.toString(task.getId()));
		txtFieldTitle.setText(task.getTitle());
		txtFieldDescription.setText(task.getDescription());
        txtFieldDate.setText(task.getDateCreated());
        
        storePrevVals(task);
        
        comboSetSelectedByValue(comboBoxState, task.getState());
        comboSetSelectedByValue(comboBoxAssignee, task.getAssignee());
		
		JButton btnDone = new JButton("Done");
		btnDone.setBounds(403, 432, 117, 25);
		getContentPane().add(btnDone);
		
		btnDone.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {	
			if (needsUpdate()) {
				String sql = "UPDATE tasks SET title = ?, description = ?, assignee = ?, state = ? WHERE id = ?";
				Connection conn = JavaConnector.ConnectDb();		
				try { 
					PreparedStatement pst = conn.prepareStatement(sql);
			  
					pst.setString(1, txtFieldTitle.getText());
					pst.setString(2, txtFieldDescription.getText());
					pst.setString(3, comboBoxAssignee.getSelectedItem().toString());
					pst.setString(4, comboBoxState.getSelectedItem().toString());
					pst.setInt(5, task_.getId());
			        
		            pst.execute();
		            TaskListWindow.refreshTable();

				} catch (Exception e) {
					JOptionPane.showConfirmDialog(null, e);
			    }

				
			
			}
			
			dispose();
			}
		});
	}
}
