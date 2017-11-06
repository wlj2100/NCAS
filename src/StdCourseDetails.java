import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.sql.*;

import com.mysql.jdbc.ResultSet;
public class StdCourseDetails {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/project3-nudb";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "password";

private String username;
public StdCourseDetails(String StudID){
	username = StudID;		
	
}


public void studentcourses(){
	Connection conn = null;
	Statement stmt = null;
	Input in = new Input();
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
	   sql = "select T.uoscode,T.grade from transcript T where T.StudId = " +username;
       java.sql.ResultSet rs = stmt.executeQuery(sql);
       while(rs.next()){
    	      // int i  = rs.getInt("id");
    	      //   int age = rs.getInt("name");
    	       String tmp1 = null;
    	       String tmp2 = null;
    	       tmp1 = rs.getString("UoScode");
    	       tmp2 = rs.getString("grade");
    	      System.out.println("UoScode  " +tmp1 + "    Grade " + tmp2);
    	    
       }
       System.out.println("C --- To enter course number :");
	   System.out.println("B --- Back to Menu");
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
public void studcourses(){
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
	      Scanner coursenumber = new Scanner (System.in);
	       String cn = coursenumber.nextLine();
	       sql = "select U.uoscode,US.uosname,U.year,U.semester,U.enrollment,U.maxenrollment,F.name,T.grade from faculty F,uosoffering U natural join transcript T natural join unitofstudy US where F.id = U.InstructorId and uoscode ='" +cn + "'and T.StudId = " +username;
	       java.sql.ResultSet rs = stmt.executeQuery(sql);
	       //Retrieve by column name
	       while(rs.next()){
	        // int i  = rs.getInt("id");
	        //   int age = rs.getInt("name");
	         String tmp = null;
	         tmp = rs.getString("UoScode");
	        System.out.println("UoScode -- " +tmp);
	         tmp = rs.getString("uosname");
	        System.out.println("UoSname -- " +tmp);
	        int i  = rs.getInt("Year");
	        System.out.println("Year -- " +tmp);
	           i = rs.getInt("Enrollment");
	        System.out.println("Enrollment -- " +i); 
	           i = rs.getInt("Maxenrollment");
	        System.out.println("MaxEnrollment -- " +i);
	           tmp = rs.getString("name");
	        System.out.println("name -- " +tmp);  
	         tmp = rs.getString("grade");
	        System.out.println("Grade -- " +tmp);
	        
	 	       }
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

