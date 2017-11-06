import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Withdraw {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/project3-nudb";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "password";
	
	//private String withdraw_ccode;
	private String withdraw_studId;
	//private String yearNow;
	//private String quarterNow;
	
	public Withdraw(String studId){
		//withdraw_ccode = c_code;
		withdraw_studId = studId;
		//yearNow = year;
		//quarterNow = quarter;
	}
	
	public void doWithdraw(){
		// Withdraw test
		   Withdraw w = new Withdraw(withdraw_studId);
		   Input in = new Input();
		   w.ShowCourse();
		   String withdraw_ccode = in.getInput("Please input the code of the course you want to withdraw:");
		   String year = in.getInput("Please input the year of the course you want to withdraw:");
		   String quarter = in.getInput("Please input the quarter of the course you want to withdraw:");
		   //System.out.println(withdraw_ccode);
		   if (w.checkCourseEnroll(withdraw_ccode)){
			   w.WithdrawCourse(withdraw_ccode, year, quarter);
			   System.out.println("The course has been withdrawn!");
		   }
		   else{
			   System.out.println("You can only withdraw a course that you are enrolled in and have not finished.");
		   }
	}
	
	public void ShowCourse(){
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
		      sql = "select T.UoSCode, U.UoSName, T.Year, T.Semester from (transcript T join unitofstudy U) where T.UoSCode = U.UoSCode and T.StudId = " + withdraw_studId + " and T.Grade is null";
		      //System.out.println(sql);
		      ResultSet rs = stmt.executeQuery(sql);

		      //STEP 5: Extract data from result set
		      while(rs.next()){ 
		      String row = rs.getString("UoSCode");
		      System.out.print(row);
		      System.out.print("   ");
		      row = rs.getString("Year");
		      System.out.print(row);
		      System.out.print("   ");
		      row = rs.getString("Semester");
		      System.out.print(row);
		      System.out.print("   ");
		      row = rs.getString("UoSName");
		      System.out.print(row);
		      System.out.println("   ");
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

	
	public void WithdrawCourse(String c_code, String yearNow, String quarterNow){
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
		      sql = "call withdraw('" + c_code + "', " + withdraw_studId + ", "+ yearNow + ", '" + quarterNow + "')";
		     // System.out.println(sql);
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
	
	public boolean checkCourseEnroll(String c_code){
		Connection conn = null;
		Statement stmt = null;
		boolean is_enroll = false;
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
		      sql = "SELECT UoSCode from transcript WHERE StudId = "  + withdraw_studId + " and Grade is null";
		      //System.out.println(sql);
		      ResultSet rs = stmt.executeQuery(sql);

		      //STEP 5: Extract data from result set
		      while(rs.next()){ 
		    	  String row = rs.getString("UoSCode");
		    	  //System.out.println(row);
		    	  if (c_code.equals(row)){
		    		  is_enroll = true;
		    	  }
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
		return is_enroll;
	}
	

}