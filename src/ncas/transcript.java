package ncas;

import java.util.*;
import java.sql.*;

public class transcript {
	private String id;
	private String transcript = null;
	private static final String WELCOME = "Your transcript detail:";
	private static final String TRANSCRIPT_SQL = "select T.uoscode,T.grade from transcript T where T.StudId = ";
	private static final String COURSE_SQL = "select U.uoscode,US.uosname,U.year,U.semester,U.enrollment,U.maxenrollment,F.name,T.grade from faculty F,uosoffering U natural join transcript T natural join unitofstudy US where F.id = U.InstructorId and uoscode ='";
	public transcript(String id) {
		this.id = id;
	}
	public void menu() {
		print.print(WELCOME);
		transcript = getTranscript(id);
		print.print(transcript);
		while (true) {
			print.print("Option:");
			print.print("c --- Course Detail");
			print.print("b --- Back to Menu");
			Scanner sc = new Scanner(System.in);
			String option = sc.nextLine().toLowerCase();
//			sc.close();
			boolean exit = false;
			switch(option) {
			case "c":
				print.print("Please input course number:");
				sc = new Scanner(System.in);
				String courseNum = sc.nextLine();
				print.print(getCourseDetail(courseNum));
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
	private String getCourseDetail(String courseNum) {
		StringBuilder sb = new StringBuilder();
		String sql = COURSE_SQL + courseNum + "'and T.StudId = " +id;
		try {
			Class.forName(client.JDBC_DRIVER);  
			Connection con = DriverManager.getConnection(  
			client.DB_URL, client.USER, client.PASS);   
			Statement stmt = con.createStatement();  
			ResultSet rs = stmt.executeQuery(sql); 
// the course number and title, 
// the year and quarter when the student took the course, 
// the number of enrolled students, 
// the maximum  enrollment  and  the  lecturer  (name),  
// the grade scored  by  the student.
			while(rs.next()) {
				sb.append("\nUoScode: ");
				sb.append(rs.getString("UoScode"));
				sb.append("\nUoSname: ");
				sb.append(rs.getString("UoSname"));
				sb.append("\nYear: ");
				sb.append(rs.getInt("Year"));
				sb.append("\nEnrollment: ");
				sb.append(rs.getInt("Enrollment"));
				sb.append("\nMaxenrollment: ");
				sb.append(rs.getInt("Maxenrollment"));
				sb.append("\nLecture: ");
				sb.append(rs.getString("name"));
				sb.append("\nGrade: ");
				sb.append(rs.getString("Grade"));
				sb.append('\n');
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return sb.toString();
	}
	private String getTranscript(String id) {
		String sql = TRANSCRIPT_SQL + id;
		StringBuilder sb = new StringBuilder();
		try {
			Class.forName(client.JDBC_DRIVER);  
			Connection con = DriverManager.getConnection(  
			client.DB_URL, client.USER, client.PASS);   
			Statement stmt = con.createStatement();  
			ResultSet rs = stmt.executeQuery(sql); 
			while(rs.next()) {
				sb.append("UoScode: ");
				sb.append(rs.getString("UoScode"));
				sb.append(", Grade: ");
				sb.append(rs.getString("Grade"));
				sb.append('\n');
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return sb.toString();
	}
}
