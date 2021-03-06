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

public  class MainWindow extends JFrame {

	private JTable table_1;
	private Connection connection = null;
	private int taskNum_;
	/**
	 * Launch the application.
	 */
	public void display() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public JTable getTable() {
		return table_1;
	}
	
	public int getTaskNum() {
		return taskNum_;
	}
	
	public void refreshTable() { 
		//clearTable();
		try {
			
			String query = "select id, title, assignee, state  from tasks ";			
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();			
			table_1.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		taskNum_ = table_1.getRowCount();
	}
	
	/**
	 * Create the frame.
	 */
	public MainWindow() {
		connection = JavaConnector.ConnectDb();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(200, 200, 830, 484);
		
		getContentPane().setLayout(null);
		
		JButton btnAddTask = new JButton("Add");
		btnAddTask.addActionListener(new AddTask(this));
		btnAddTask.setBounds(23, 12, 66, 27);
		getContentPane().add(btnAddTask);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 51, 783, 318);
		getContentPane().add(scrollPane);
		
		table_1 = new JTable();
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{},
			},
			new String[] {
			}
		));
		scrollPane.setViewportView(table_1);
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new EditTask(this));
		btnEdit.setBounds(740, 12, 76, 27);
		getContentPane().add(btnEdit);
		refreshTable();
	}

}

