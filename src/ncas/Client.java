package ncas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import ncas.print;
import ncas.Transcript;



public class Client {
	//  Database info and credentials
	protected static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	protected static final String DB_URL = "jdbc:mysql://localhost:3306/project3-nudb";
	protected static final String USER = "root";
	protected static final String PASS = "9473";
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
			String sql = "SELECT Password from Student WHERE ID = "  + id;
			try {
				Class.forName(Client.JDBC_DRIVER);  
				Connection con = DriverManager.getConnection(  
				Client.DB_URL, Client.USER, Client.PASS);   
				Statement stat = con.createStatement();  
				ResultSet rs = stat.executeQuery(sql); 
				boolean exit = false;
				if (rs.next() && password.equals(rs.getString("Password"))) {
					exit = menu(id);
				} else {
					print.print("wrong password");
				}
				rs.close();
				stat.close();
				con.close();
				if (exit) break;
			} catch (Exception e) {
				print.print("wrong id or database access error");
				break;
			}
		}
		print.print("System end");
		System.exit(0);
	}
	private static boolean menu(String id) {
		print.print("Hello, id:" + id);
		while (true) {
			print.print("The courses currently enrolled are :\n");
			Course cur_course = new Course(id);
			cur_course.current_course();
			print.print("Select a option:");
			print.print("1 -> Transcript");
			print.print("2 -> Enroll");
			print.print("3 -> Withdraw");
			print.print("4 -> Personal Detail");
			print.print("Please type the option you want, type q for quit");
			Scanner sc = new Scanner(System.in);
			String option = sc.nextLine().toLowerCase();
			switch(option) {
			case "1":
				print.print("Transcript");
				Transcript tranObj = new Transcript(id);
				tranObj.menu();
				break;
			case "2":
				print.print("Enroll");
				Enrollment enObj = new Enrollment(id);
				enObj.menu();
				break;
			case "3":
				print.print("Withdraw");
				withdraw w = new withdraw(id);
				w.drop();
				break;
			case "4":
				print.print("Personal Detail");
				PersonalDetail pdObj = new PersonalDetail(id);
				pdObj.menu();
				break;
			case "q":
				print.print("Thank you for using this system, bye~");
				return false;
			case "exit":
				print.print("System shut down");
				return true;
			default:
				print.print("Please choose the right option");
				break;
			}
		}
	}
}
