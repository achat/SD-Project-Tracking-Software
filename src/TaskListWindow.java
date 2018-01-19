import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import java.awt.CardLayout;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.JSplitPane;
import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextPane;
import java.awt.List;
import java.awt.Point;

import javax.swing.JTable;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.sql.*;
import javax.swing.*;

public  class TaskListWindow extends JFrame {


	static Connection conn = null;
	static int taskNum_;
	private static JTable taskListTable;
	private JButton btnAddTask;
	private JButton btnEditTask;
	
	public TaskListWindow() {
		initComponents();
		conn = JavaConnector.ConnectDb();
		refreshTable();
	}
	
	private void initComponents() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnAddTask = new javax.swing.JButton();
		btnEditTask = new javax.swing.JButton();
		taskListTable = new javax.swing.JTable();
		btnAddTask.setText("Add");
		btnEditTask.setText("Edit");
		
		btnAddTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TaskWindow.display(null);
			}	
		});
		
		btnEditTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selection = taskListTable.getSelectedRow();
				if (selection != -1) { 
					Task task = new Task();
					task.fillFromDb(selection);
					TaskWindow.display(task);
				} else {
					System.out.println("No task selected for edit!");
				}
			}
		});
		
		taskListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		taskListTable.setModel(new DefaultTableModel(
			new Object[][] {
				{},
			},
			new String[] {
			}
		));
		
		setBounds(200, 200, 830, 484);
		getContentPane().setLayout(null);
		btnAddTask.setBounds(23, 12, 66, 27);
		getContentPane().add(btnAddTask);		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 51, 783, 318);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(taskListTable);		
		btnEditTask.setBounds(740, 12, 76, 27);
		getContentPane().add(btnEditTask);
	
	}
		
	public static void display() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskListWindow frame = new TaskListWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void refreshTable() { 
		//clearTable();
		try {
			
			String query = "select id, title, assignee, state  from tasks ";			
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();			
			taskListTable.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		taskNum_ = taskListTable.getRowCount();
	}
}

