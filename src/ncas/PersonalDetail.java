package ncas;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import ncas.Client;

public class PersonalDetail {
	private String id = null;
	private String personalDetail = null;
	private static final String WELCOME = "Your personal information details:";	
	private static final String PD_SQL = "select * from student S where S.id = ";
	public PersonalDetail(String id) {
		this.id = id;
	}
	public void menu() {
		print.print(WELCOME);
		personalDetail = getPersonalDetail(id);
		print.print(personalDetail);
		while (true) {
			print.print("Option:");
			print.print("a --- Change address");
			print.print("p --- Change password");
			print.print("b --- Back to Menu");
			Scanner sc = new Scanner(System.in);
			String option = sc.nextLine().toLowerCase();
//			sc.close();
			boolean exit = false;
			switch(option) {
			case "a":
				print.print("Please input new address:");
				sc = new Scanner(System.in);
				String address = sc.nextLine();
				updatePD(id, "Address", address);
				break;
			case "p":
				print.print("Please input password:");
				sc = new Scanner(System.in);
				String password = sc.nextLine();
				updatePD(id, "Password", password);
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
	private String updatePD(String id, String colName, String newVal) {
		StringBuilder sb = new StringBuilder();
		try {
			Class.forName(Client.JDBC_DRIVER);  
			Connection con = DriverManager.getConnection(  
			Client.DB_URL, Client.USER, Client.PASS);   
			try{
				PreparedStatement pstmt = con.prepareStatement("update student set " + colName + " = ? where id = ?");
	            pstmt.setString(1, newVal);
	            pstmt.setString(2, id);
	            
            	int rs = pstmt.executeUpdate();
            	if(rs > 0){
            		print.print(colName + " update success!");
            	}
            	else{
            		print.print("Something went wrong T^T");
            	}
			}
            catch(Exception e){
            	System.out.println(e);
            }
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return sb.toString();
	}
	
	private String getPersonalDetail(String id) {
		String sql = PD_SQL + id;
		StringBuilder sb = new StringBuilder();
		try {
			Class.forName(Client.JDBC_DRIVER);  
			Connection con = DriverManager.getConnection(  
			Client.DB_URL, Client.USER, Client.PASS);   
			Statement stmt = con.createStatement();  
			ResultSet rs = stmt.executeQuery(sql); 
			while(rs.next()) {
				sb.append("Id: ");
				sb.append(rs.getString("Id"));
				sb.append(", Name: ");
				sb.append(rs.getString("Name"));
				sb.append(", Password: ");
				sb.append(rs.getString("Password"));
				sb.append(", Address: ");
				sb.append(rs.getString("Address"));
				sb.append('\n');
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return sb.toString();
	}
}
