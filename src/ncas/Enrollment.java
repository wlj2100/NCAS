package ncas;

import java.util.*;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Enrollment {
	private String id;
	private String enrollmentScreen = null;
	private static final String WELCOME = "Welcome to enrollment page. Here are the courses you can enroll in:";
	public Enrollment(String id) {
		this.id = id;
	}
	
	public void menu() {
		print.print(WELCOME);
		enrollmentScreen = availableCourses();
		print.print(enrollmentScreen);
		while (true) {
			print.print("Option:");
			print.print("e --- Enroll in a course");
			print.print("b --- Back to Menu");
			Scanner sc = new Scanner(System.in);
			String option = sc.nextLine().toLowerCase();
//			sc.close();
			boolean exit = false;
			switch(option) {
			case "e":
				print.print("Please input course number:");
				sc = new Scanner(System.in);
				String courseId = sc.nextLine();
				print.print(courseInfo(courseId));
				print.print("Please input the year:");
				sc = new Scanner(System.in);
				int year = Integer.parseInt(sc.nextLine());
				print.print("Please input the semester:");
				sc = new Scanner(System.in);
				String semester = sc.nextLine();
				enroll(id, courseId, year, semester);
				
				break;
			case "b":
				exit = true;
				break;
			default:
				break;
			}
			if (exit) break;
		}
	}
	public String availableCourses(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
		String current_date = sdf.format(new Date());
		String cur_year = current_date.substring(3,7);
		int cur_month = Integer.parseInt(current_date.substring(0,2));
		String cur_q = "";
		String next_q = "";
		String next_year = "";
		if (cur_month >= 9 && cur_month <= 12) {
			cur_q = "Q1";
			next_q = "Q2";
			next_year = Integer.parseInt(cur_year) + 1 + "";
		}
		if (cur_month >= 1 && cur_month <= 3) {
			cur_q = "Q2";
			next_q = "Q3";
			next_year = cur_year;
		}
		if (cur_month >= 4 && cur_month <= 6) {
			cur_q = "Q3";
			next_q = "Q3";
			next_year = cur_year;
		}
		if (cur_month >= 7 && cur_month <= 8) {
			cur_q = "Q4";
			next_q = "Q1";
			next_year = cur_year;
		}
		StringBuilder sb = new StringBuilder();
		try{
			Class.forName(Client.JDBC_DRIVER);  
			Connection con = DriverManager.getConnection(  
			Client.DB_URL, Client.USER, Client.PASS);   
			Statement stmt = con.createStatement(); 
			String sql = "";
			if(next_year.equals(cur_year)){
				sql = "select * from uosoffering where Year = " + cur_year + " and ( Semester = '" + cur_q + "' or Semester = '" + next_q + "');";
			}
			else{
				sql = "select * from uosoffering where (Year = " + cur_year +" and Semester = '" + cur_q + "') or (Year = " + next_year + " and Semester = '" + next_q + "');";
			}
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				sb.append("UoSCode: ");
				sb.append(rs.getString("UoSCode"));
				sb.append('\n');
			}
			con.close();
		}
		catch(Exception e) { 
			System.out.println(e);
		}
		return sb.toString();
	}
	
	public String courseInfo(String courseId){
		StringBuilder sb = new StringBuilder();
		try{
			Class.forName(Client.JDBC_DRIVER);  
			Connection con = DriverManager.getConnection(  
			Client.DB_URL, Client.USER, Client.PASS);
			String sql = "select Semester, Year from uosoffering where UoSCode = '" + courseId + "'";
			Statement stmt = con.createStatement();  
			ResultSet rs = stmt.executeQuery(sql); 
			while(rs.next()) {
				sb.append("Id: ");
				sb.append(courseId);
				sb.append('\n');
				sb.append("Semester ");
				sb.append(rs.getString("Semester"));
				sb.append(", Year ");
				sb.append(rs.getString("Year"));
				sb.append('\n');
			}
			con.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return sb.toString();
	}
	
	public void enroll(String id, String courseId, int year, String semester){
		try{
			Class.forName(Client.JDBC_DRIVER);  
			Connection con = DriverManager.getConnection(  
			Client.DB_URL, Client.USER, Client.PASS);
			CallableStatement cStmt = con.prepareCall("{call enroll(?, ?, ?, ?)}");
            cStmt.setString("ID", id);
            cStmt.setString("courseID", courseId);
            cStmt.setInt("c_year", year);
            cStmt.setString("semester", semester);
            boolean hadResults = cStmt.execute();
            int result = -1;
            while(hadResults){
            	ResultSet rs = cStmt.getResultSet();
            	while(rs.next()){
            		result = Integer.parseInt(rs.getString("result"));
            	}
            	hadResults = cStmt.getMoreResults();
            }
            System.out.println(result);
            switch(result) {
            case 0 :
            	print.print("Enroll success!");
            	break;
            case 1 :
            	print.print("You are already enrolled in this course.");
            	break;
            case 2 :
            	print.print("Sorry, the course is already full.");
            	break;
            case 3 :
            	print.print("Prerequist not meet. You need to finish these courses first:");
            	print.print(preReq(id, courseId, year, semester));
            	break;
            default:
            	break;
            }		
			con.close();
		}
		catch(Exception e){
			System.out.println(e);
		}		
	}
	
	public String preReq(String id, String courseId, int year, String semester){
		StringBuilder sb = new StringBuilder();
		String sql = "select uosoffering.UoSCode, PrereqUoSCode from uosoffering join requires where Year = " + year + " and Semester = '" + semester +"' and uosoffering.UoSCode = '" + courseId + "' and PrereqUoSCode not in (select UoSCode from transcript where StudId = " + id + ") group by PrereqUoSCode;";
		try{
			Class.forName(Client.JDBC_DRIVER);  
			Connection conn = DriverManager.getConnection(  
			Client.DB_URL, Client.USER, Client.PASS);
			Statement stmt = conn.createStatement();  
			ResultSet rs = stmt.executeQuery(sql); 
			while(rs.next()) {
				sb.append("CourseId: ");
				sb.append(rs.getString("PrereqUoSCode"));
				sb.append('\n');
			}
			conn.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return sb.toString();
	}
}
