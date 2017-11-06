import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
public class client {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/project3-nudb";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "password";
	
	
	public static void main(String[] args){
		Connection conn = null;
		Statement stmt = null;
		for (;;){
			   System.out.print("Enter ID:");
			   Scanner id = new Scanner (System.in);
			   String username = id.nextLine();
			   System.out.print("Password:");
			   Scanner password = new Scanner (System.in);
			   String psswd = password.nextLine();
			   boolean isLog = false;
			   try{
				      //STEP 2: Register JDBC driver
				      Class.forName("com.mysql.jdbc.Driver");
				   
				      //STEP 3: Open a connection
				      System.out.println("Connecting to database...");
				      conn = DriverManager.getConnection(DB_URL,USER,PASS);
				      stmt = conn.createStatement();
				       
				      String sql;
				      sql = "SELECT Password from Student WHERE ID = "  + username;
				      ResultSet rs = stmt.executeQuery(sql);
				    if (rs.next() && psswd.equals(rs.getString("Password"))){
				    	isLog = true;
				    	
				    }
				    else {
				    	
				    	System.out.println("Enter right username and password");
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
			   
			       Input in = new Input();
				   while(isLog){
					   String op = null;
					    
					   System.out.println("Logged in..");
					   System.out.println("The courses currenlty enrolled are :\n");
					   currcourses cc = new currcourses(username);
					   cc.showcurrentcourse();
			   		   System.out.println("Select an option from the Student Menu below:\n 1)Personal Details \n 2)Transcript \n 3)Enroll \n 4)Withdraw \n 5)Logout \n>>");
			   		   Scanner smenu = new Scanner (System.in);
			   		   String stdmenu = smenu.nextLine();
			   		 if(stdmenu.equals("1") || stdmenu.equals("2") || stdmenu.equals("3") || stdmenu.equals("4") || stdmenu.equals("5") ){
			   		   switch (stdmenu){
			   		   case "1" : 
			   			   System.out.println("Personal Details \n");
			   			   PersonalDetails p = new PersonalDetails(username);
			   			   p.PersonalDetailsOption();
			   			   break;
			   		   case "2" :
			   			   System.out.println("Transcript \n");
			   			   StdCourseDetails scd = new StdCourseDetails(username);
			   			   scd.studentcourses();
			   			   String detailOption = in.getInput(">>");
			   			   if (detailOption.equals("C"))
			   				System.out.println("Enter the course number for details \n>>");
			   				   scd.studcourses();
			   			   break;
			   		   case "3":
			   			   System.out.println("Enroll \n");
			   			   Enrollment en = new Enrollment(username);
			   			   en.Enroll();
			   			   break;
			   		   case "4":
			   			   System.out.println("Withdraw \n");
			   			   Withdraw w = new Withdraw(username);
			   			   w.doWithdraw();
			   			   break;
			   		   case "5":
			   			   System.out.println("Logout");
			   			   isLog = false;
			   			   break;
			   			   
			   			default :
			   				System.out.println("Choose the right option");
			   				break;
			   		      }

			   		 }
			   		 
			   		 else{
			   			 
			   			System.out.println("Choose the right option");
			   		 }
					  
					  
					   
		 }
			
}
		

}
}