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

	private JLayeredPane contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_5;
	private JTextField textField_3;
	private JTextField textField_4;
	private JComboBox comboBox_1;
	private Connection connection = null;
    PreparedStatement pst = null;
    private String timeStamp_;
    private JComboBox comboBox;
    
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
    	System.out.println("Eimai add Task");
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
	        	comboBox.addItem(name);
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
		
		JLabel lblNewLabel = new JLabel("Task ID");
		lblNewLabel.setBounds(40, 53, 70, 15);
		getContentPane().add(lblNewLabel);
		
		JLabel lblAssignee = new JLabel("Assignee");
		lblAssignee.setBounds(40, 158, 70, 35);
		getContentPane().add(lblAssignee);
		
		JLabel lblBrief = new JLabel("Title");
		lblBrief.setBounds(40, 89, 70, 15);
		getContentPane().add(lblBrief);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(37, 276, 107, 15);
		getContentPane().add(lblDescription);
		
		textField_2 = new JTextField();
		textField_2.setEnabled(false);
		textField_2.setEditable(false);
		textField_2.setBounds(160, 51, 165, 24);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);
		textField_2.setText(Integer.toString(MainWindow.taskNum_ + 1));
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(157, 274, 306, 146);
		getContentPane().add(textField_5);
		
		comboBox = new JComboBox();
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Unassigned"}));
		comboBox.setBounds(160, 163, 165, 30);
		getContentPane().add(comboBox);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(160, 87, 391, 24);
		getContentPane().add(textField_3);

		JLabel lblState = new JLabel("State");
		lblState.setBounds(40, 129, 70, 15);
		getContentPane().add(lblState);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"New", "Under Dev", "Completed", "Closed"}));
		comboBox_1.setBounds(160, 121, 165, 30);
		getContentPane().add(comboBox_1);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(40, 210, 70, 35);
		getContentPane().add(lblDate);
		
		textField_4 = new JTextField();
		textField_4.setEnabled(true);
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		textField_4.setBounds(160, 218, 165, 24);
		textField_4.setText(timeStamp_);
		getContentPane().add(textField_4);

		appendUsersToCombo();
	}

	public TaskWindow() {
		createWinCore();
		
		JButton btnDone = new JButton("Done");
		btnDone.setBounds(403, 432, 117, 25);
		getContentPane().add(btnDone);
		
		btnDone.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {	
		String sql = "Insert into tasks (title, description, state, assignee, date_created) values (?, ?, ?, ?, ?)";

		    try {
		    	PreparedStatement pst = null;
		    	pst = connection.prepareStatement(sql);
		    	Task task = new Task();
		        task.setTitle(textField_3.getText());
		        task.setDescription(textField_5.getText());
		        task.setState(String.valueOf(comboBox_1.getSelectedItem()));
		        task.setAssignee(String.valueOf(comboBox.getSelectedItem()));
		        
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
		        	MainWindow.refreshTable();
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
		if ((textField_3.getText() != prevTitle_) ||
				(textField_5.getText() != prevDescription_) ||
				(textField_4.getText() != prevDateCreated_) ||
				(prevAssignee_ != comboBox.getSelectedItem().toString()) ||
				(prevState_ != comboBox_1.getSelectedItem().toString()))
			{
				return true;
			}
		return false;
				
	}
	
	public TaskWindow(Task task) {
		createWinCore();
		task_ = task;
		
		textField_2.setText(Integer.toString(task.getId()));
		textField_3.setText(task.getTitle());
		textField_5.setText(task.getDescription());
        textField_4.setText(task.getDateCreated());
        
        storePrevVals(task);
        
        comboSetSelectedByValue(comboBox_1, task.getState());
        comboSetSelectedByValue(comboBox, task.getAssignee());
		
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
			  
					pst.setString(1, textField_3.getText());
					pst.setString(2, textField_5.getText());
					pst.setString(3, comboBox.getSelectedItem().toString());
					pst.setString(4, comboBox_1.getSelectedItem().toString());
					pst.setInt(5, task_.getId());
			        
		            pst.execute();
		            MainWindow.refreshTable();

				} catch (Exception e) {
					JOptionPane.showConfirmDialog(null, e);
			    }

				
			
			}
			
			dispose();
			}
		});
	}
}
