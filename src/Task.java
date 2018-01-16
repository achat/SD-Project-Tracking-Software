import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class Task {
	
	private int    id_;
	
    private String title_;
	
	private String description_;
	
	private String assignee_;
	
	private String state_;
	
	private String date_created_;
	
	public void represent() {
		System.out.println("REPRESENTATION");
		System.out.println(getTitle());
		System.out.println(getDescription());
		System.out.println(getAssignee());
		System.out.println(getState());
		System.out.println(getDateCreated());
	}
	
	public void fillFromDb(int id) {
		id = id + 1;
	    String sql = "SELECT * FROM tasks WHERE id = ?";
		Connection conn = JavaConnector.ConnectDb();		
		try { 
			PreparedStatement pst = conn.prepareStatement(sql);
	  
			pst.setInt(1, id);
	        ResultSet rs = pst.executeQuery();
            rs = pst.executeQuery();
            if (rs.next()) {
            	this.setId(id);
            	this.setTitle(rs.getString("title"));
            	this.setDescription(rs.getString("description"));
            	this.setAssignee(rs.getString("assignee"));
            	this.setState(rs.getString("state"));
            	this.setDateCreated(rs.getString("date_created"));
            }

		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, e);
	    }
	    
	
	}

	public String getTitle() {
		return title_;
	}

	public void setTitle(String title_) {
		this.title_ = title_;
	}

	public String getDescription() {
		return description_;
	}

	public void setDescription(String description_) {
		this.description_ = description_;
	}

	public String getAssignee() {
		return assignee_;
	}

	public void setAssignee(String assignee_) {
		this.assignee_ = assignee_;
	}

	public String getState() {
		return state_;
	}

	public void setState(String state_) {
		this.state_ = state_;
	}

	public String getDateCreated() {
		return date_created_;
	}
	
	public void setDateCreated(String date_created_) {
		this.date_created_ = date_created_;
	}

	public int getId() {
		return id_;
	}

	public void setId(int id_) {
		this.id_ = id_;
	}
	
	
	
}
