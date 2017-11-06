import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Enrollment {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/project3-nudb";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "password";
		
	//private String withdraw_ccode;
	private String username;
	private String yearNow;
	private String quarterNow;
	  
    
	

	public Enrollment(String studid){
		username = studid;
		
	}

	
	public void showCourses(){
		   String yearnext ;
		   String quarter1 = null;
		   Date date=new Date();  
		   SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM");  
		   String time=formatter.format(date);  
		   String yearcurrent = time.substring(0,4);
		   String monthStr = time.substring(5,7);
		   int month = Integer.parseInt(monthStr);
     		yearnext   = yearcurrent;
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
		   
		   if (quarter == "Q1"){
			   quarter1 = "Q2";
			   int year = Integer.parseInt(yearnext);
			   year++;
			   yearnext = Integer.toString(year);
			   
		   }
		   if (quarter == "Q2"){
			   quarter1 = "Q3";
		   }
		   if (quarter == "Q3"){
			   quarter1 = "Q4";
		   }
		   if (quarter == "Q4"){
			   quarter1 = "Q1";
		   }
		   
		   //System.out.println(year);
		   //System.out.println(quarter);
		   
		   PersonalDetails pd = new PersonalDetails(username);
		   String maxprefenrollment= null;
		   String nonpreferedclass=null;
		   maxprefenrollment = pd.getMaxPreferredEnroll();
		   nonpreferedclass = pd.getNonPreferredClassroomType();
           if (maxprefenrollment == null){
        	   maxprefenrollment = "null";
           }
           if (nonpreferedclass == null){
        	   nonpreferedclass = "null";
           }
           else{
        	   nonpreferedclass = "'" + nonpreferedclass + "'";
           }
           
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
   		      String sql,sql1;
		   sql = "call showcourseswithpersonal(" + username + ", " + yearcurrent + ", '"+ quarter +"', "+ maxprefenrollment + " , "+ nonpreferedclass +");";
		   
		   ResultSet rs = stmt.executeQuery(sql);
		   //System.out.println("kkkkkkk" + sql);
		 
		  
		      //STEP 5: Extract data from result set
		      while (rs.next()){
		    	  String uoscode = rs.getString("UoSCode");
		    	  String year1 = rs.getString("year");
		    	  String sem = rs.getString("Semester");
		    	  System.out.println("UoScode  " +uoscode + " year " + year1 +" semester " + sem);
		   
           } 
		      
		      sql1 = "call showcourseswithpersonal(" + username + ", " + yearnext + ", '"+ quarter1 +"', "+ maxprefenrollment + " , "+ nonpreferedclass +");";
		      ResultSet rsc = stmt.executeQuery(sql1);
		      while (rsc.next()) {
		    	  String uoscode1 = rsc.getString("UoSCode");
		    	  String year2 = rsc.getString("year");
		    	  String sem1 = rsc.getString("Semester");
		    	  System.out.println("UoScode  " +uoscode1 + " year " + year2 + " semester " + sem1);
           }
              rsc.close();
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
    public void Enroll(){
    	Enrollment en = new Enrollment(username);
    	en.showCourses();
    	Input in = new Input();
    	String CCode = null;
    	CCode = in.getInput("Enter the course code:");
    	yearNow = in.getInput("Enter the year:");
    	quarterNow = in.getInput("Enter the semester:");
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
		      sql = "SELECT UoSCode, Semester , Year from belowMaxEnrollment Where UoSCode = '" + CCode + "';";
		      //System.out.println(sql);
		      ResultSet rs = stmt.executeQuery(sql);

		      //STEP 5: Extract data from result set
		      while (rs.next()){
		    	  String year = rs.getString("Year");
		    	  String quarter = rs.getString("Semester");
		    	  if (year.equals(yearNow) && quarter.equals(quarterNow)){
		    		  System.out.println("Warning: The number of enrollment of this course is below 50%!");
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
		en.Check(CCode, yearNow, quarterNow);
    }
		

	
	public void Check(String UoSCode, String yearNow, String quarterNow){
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
		      sql = "call checkrequires(" + username +", '"+ UoSCode + "', @tmp);";
		      //System.out.println(sql);
		      ResultSet rs = stmt.executeQuery(sql);
		      sql = "select @tmp;";
		      rs = stmt.executeQuery(sql);

		      //STEP 5: Extract data from result set
		      rs.next();
		      String tmp = null;
		      tmp = rs.getString("@tmp");
		     // System.out.println ("//////////////////////////////////////////////" +tmp);
		      if (tmp.equals("1")){
		    	  System.out.println("You have to take the prerequisites!");
		    	  sql = "select P.PrereqUoSCode from (select PrereqUoSCode from requires where UoSCode = '"+ UoSCode +"') as P where P.PrereqUoSCode not in (select UoSCode from transcript where StudId = " + username + " and (Grade = 'P' or Grade = 'CR'));";
		    	  rs = stmt.executeQuery(sql);
		    	  while(rs.next()){
		    		  String row = rs.getString("PrereqUoSCode");
		    		  System.out.println(row);
		    	  }
		      }
		      else{
		    	  sql = "call checkenrollment('" + UoSCode + "', "+ yearNow + ", '" + quarterNow + "', @tmp1);";
		    	  rs = stmt.executeQuery(sql);
		    	  sql = "select @tmp1;";
			      rs = stmt.executeQuery(sql);
			      rs.next();
		    	  tmp = rs.getString("@tmp1");
		    	  if (tmp.equals("1")){
		    		  System.out.println("The course is full!");
		    	  }
		    	  else{
		    		  sql = "call checkcurrent(" + username + ", '" + UoSCode + "', @tmp2);";
		    		  rs = stmt.executeQuery(sql);
			    	  sql = "select @tmp2;";
				      rs = stmt.executeQuery(sql);
				      rs.next();
			    	  tmp = rs.getString("@tmp2");
			    	  if (tmp.equals("1")){
			    		  System.out.println("You have taken this course!");
			    	  }
			    	  else{
			    		  sql = "call enrollment(" + username + ", '" + UoSCode + "', "+ yearNow + ", '" + quarterNow + "');";
			    		  rs = stmt.executeQuery(sql);
			    		  System.out.println("The course is enrolled successfully!");
			    	  }
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
	}
}

