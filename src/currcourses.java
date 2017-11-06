import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class currcourses {
	// JDBC driver name and database URL
		static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		static final String DB_URL = "jdbc:mysql://localhost:3306/project3-nudb";

		//  Database credentials
		static final String USER = "root";
		static final String PASS = "password";
	private String username;
	public currcourses(String StudID){
		username = StudID;		
		
	}


	public void showcurrentcourse(){
		Connection conn = null;
		Statement stmt = null;
		

		   Date date=new Date();  
		   SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM");  
		   String time=formatter.format(date);  
		   String year = time.substring(0,4);
		   String monthStr = time.substring(5,7);
		   int month = Integer.parseInt(monthStr);
		   String quarter = "";
		   if (month >= 9 && month <= 12){
			   quarter = "Q1";
		   }
		   if (month >= 1 && month <= 3){
			   quarter = "Q2";
		   }
		   if (month >= 4 && month <= 6){
			   quarter = "Q3";
		   }
		   if (month >= 7 && month <= 8){
			   quarter = "Q4";
		   }
		  // System.out.println(year);
		  // System.out.println(quarter);
		   
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
		      sql = "Select UoSCode from transcript where semester = '" + quarter + "'and year = " + year + " and Grade is null and StudID = " +username ;
		     // System.out.println(sql);
		      ResultSet rs = stmt.executeQuery(sql);
		      while(rs.next()){
		          String first = rs.getString("UoSCode");
			      System.out.println(first);
			     
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
	 }
	
 }





	