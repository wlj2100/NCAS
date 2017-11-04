package Management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import Management.print;
import Management.course;

public class client {
	//  Database info and credentials
	protected static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	protected static final String DB_URL = "jdbc:mysql://localhost:3306/project3-nudb";
	protected static final String USER = "root";
	protected static final String PASS = "";
	private static Connection conn = null;
	private static Statement stmt = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		///
		while (true) {
			print.print("Enter ID:");
			Scanner sc1 = new Scanner(System.in);
			String id = sc1.nextLine();
			print.print("Enter Password:");
			Scanner sc2 = new Scanner(System.in);
			String password = sc2.nextLine();
			try {
				Class.forName(JDBC_DRIVER);
				print.print("connect to database");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt = conn.createStatement();
				String sql = "SELECT Password from Student WHERE ID = "  + id;
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next() && password.equals(rs.getString("Password"))) {
					if (menu(id) == 0) {
						print.print("System end");
						return;
					}
				} else {
					print.print("wrong id or password");
					continue;
				}
			} catch(SQLException se){
				//Handle errors for JDBC
			    se.printStackTrace();
			}catch(Exception e){
			    //Handle errors for Class.forName
			    e.printStackTrace();
			}finally{
			    //finally block used to close resources
			    try{
			    		if(stmt!=null) stmt.close();
			    }catch(SQLException se2){
			    }// nothing we can do
			    try{
			    		if(conn!=null) conn.close(); 
			    	} catch(SQLException se){
			    		se.printStackTrace();
			    }//end finally try
			}//end try
		}
	}
	private static int menu(String id) {
		course cur_course = new course(id);
		print.print("Hello, id:" + id);
		print.print("The courses currenlty enrolled are :\n");
		while (true) {
			print.print("Select a option:");
			print.print("1 -> Trancscript");
			print.print("2 -> Enroll");
			print.print("3 -> Withdraw");
			print.print("4 -> Personal Detail");
			print.print("Please type the option you want, type q for quite");
			Scanner sc = new Scanner(System.in);
			String option = sc.nextLine();
			switch(option) {
			case "1":
				print.print("Transcript");
				break;
			case "2":
				print.print("Entroll");
				break;
			case "3":
				print.print("Withdraw");
				break;
			case "4":
				print.print("Personal Detail");
				break;
			case "q":
				print.print("Thank you for using this system, bye~");
				return 1;
			default:
				print.print("Please choose the right option");
				break;
			}
		}
	}
	

}
