//package database_project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonalDetails {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/project3-nudb";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "password";
	
	private String studId;

	public PersonalDetails(String Id){
		studId = Id;
	}
	
	public void PersonalDetailsOption(){
		Input in = new Input();
		PersonalDetails p = new PersonalDetails(studId);
		   System.out.print("MaximumPreferredEnrollment:");
		   System.out.println(p.getMaxPreferredEnroll());
		   System.out.print("NonPreferredClassroomType:");
		   System.out.println(p.getNonPreferredClassroomType());
		   System.out.println("");
		   System.out.println("C --- Change the details");
		   System.out.println("B --- Back to Menu");
		   String detailOption = in.getInput("Please enter your option:");
		   if (detailOption.equals("C")){
			   String maxPE = in.getInput("Please input the new MaximumPreferredEnrollment:");
			   String nonPCT = in.getInput("Please input the new NonPreferredClassroomType:");
			   p.setMaxPreferredEnroll(maxPE);
			   p.setNonPreferredClassroomType(nonPCT);
			   System.out.println("The Detail has changed!");
			   System.out.print("MaximumPreferredEnrollment:");
			   System.out.println(p.getMaxPreferredEnroll());
			   System.out.print("NonPreferredClassroomType:");
			   System.out.println(p.getNonPreferredClassroomType());
		   }
	}
	
  	public String getMaxPreferredEnroll(){
		Connection conn = null;
		Statement stmt = null;
		String MaxPreferredEnroll = null;
		try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");
		   
		      //STEP 3: Open a connection
		      //System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      
		      //STEP 4: Execute a query
		      //System.out.println("Creating statement...");
		      stmt = conn.createStatement();
		      String sql;
		      sql = "SELECT MaximumPreferredEnrollment from student WHERE Id = "  + studId;
		      //System.out.println(sql);
		      ResultSet rs = stmt.executeQuery(sql);

		      //STEP 5: Extract data from result set
		      if (rs.next()){
		    	  MaxPreferredEnroll = rs.getString("MaximumPreferredEnrollment");
		      }
		    	  
		      //STEP 6: Clean-up environment
		      rs.close();
		      stmt.close();
		      conn.close();
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		return MaxPreferredEnroll;
	}
	
	public String getNonPreferredClassroomType(){
		Connection conn = null;
		Statement stmt = null;
		String NonPreferredClassroomType = null;
		try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");
		   
		      //STEP 3: Open a connection
		      //System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      
		      //STEP 4: Execute a query
		      //System.out.println("Creating statement...");
		      stmt = conn.createStatement();
		      String sql;
		      sql = "SELECT NonPreferredClassroomType from student WHERE Id = "  + studId;
		      //System.out.println(sql);
		      ResultSet rs = stmt.executeQuery(sql);

		      //STEP 5: Extract data from result set
		      if (rs.next()){
		    	  NonPreferredClassroomType = rs.getString("NonPreferredClassroomType");
		      }
		    	  
		      //STEP 6: Clean-up environment
		      rs.close();
		      stmt.close();
		      conn.close();
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		return NonPreferredClassroomType;
	}
	
	public void setMaxPreferredEnroll(String MaxPreferredEnroll){
		Connection conn = null;
		Statement stmt = null;
		try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");
		   
		      //STEP 3: Open a connection
		      //System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      
		      //STEP 4: Execute a query
		      //System.out.println("Creating statement...");
		      stmt = conn.createStatement();
		      String sql;
		      sql = "call setMaximumPreferredEnrollment(" + studId + ", " + MaxPreferredEnroll + ")";
		      //System.out.println(sql);
		      ResultSet rs = stmt.executeQuery(sql);

		      //STEP 6: Clean-up environment
		      rs.close();
		      stmt.close();
		      conn.close();
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
	}
	
	public void setNonPreferredClassroomType(String NonPreferredClassroomType){
		Connection conn = null;
		Statement stmt = null;
		try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");
		   
		      //STEP 3: Open a connection
		      //System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      
		      //STEP 4: Execute a query
		      //System.out.println("Creating statement...");
		      stmt = conn.createStatement();
		      String sql;
		      sql = "call setNonPreferredClassroomType(" + studId + ", '" + NonPreferredClassroomType + "')";
		      //sql = "UPDATE student SET NonPreferredClassroomType = '" + NonPreferredClassroomType + "' WHERE Id = "  + studId;
		      //System.out.println(sql);
		      ResultSet rs = stmt.executeQuery(sql);

		      //STEP 6: Clean-up environment
		      rs.close();
		      stmt.close();
		      conn.close();
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
	}

}
