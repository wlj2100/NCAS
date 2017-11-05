package Management;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class course {
	private static final String JDBC_DRIVER = client.JDBC_DRIVER;  
	private static final String DB_URL = client.DB_URL;
	private static final String USER = client.USER;
	private static final String PASS = client.PASS;
	private String student_id;
	public course(String id) {
		student_id = id;
	}

	public void current_course() {
		Connection conn = null;
		Statement stmt = null;

		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
		String current_date = sdf.format(new Date());
		String cur_year = current_date.substring(3,7);
		int cur_month = Integer.parseInt(current_date.substring(0,2));
		String q = "";
		if (cur_month >= 9 && cur_month <= 12) {
			q = "Q1";
		}
		if (cur_month >= 1 && cur_month <= 3) {
			q = "Q2";
		}
		if (cur_month >= 4 && cur_month <= 6) {
			q = "Q3";
		}
		if (cur_month >= 7 && cur_month <= 8) {
			q = "Q4";
		}
		//System.out.println(cur_year + cur_month);
		try{
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = conn.createStatement();

			String sql;

			sql = "Select UoSCode from transcript where semester = '" + q + "' and year = " + cur_year + " and Grade is null and StudID = " + student_id;

			//sql = "Select UoSCode from transcript where semester = " + "'Q1'" + " and year = " + 2015 + " and Grade is null and StudID = " + student_id;
			//System.out.println(sql);

			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				String first = result.getString("UoSCode");
				System.out.println(first);
			}

			result.close();
			stmt.close();
			conn.close();
		}
		catch(Exception e) { // sql exception is included
			e.printStackTrace();
		}

		finally{
			try{
				if(stmt!=null) {
					stmt.close();
				}
			}

			catch(SQLException e2) {
				e2.printStackTrace();
			}
			try {
				if(conn!=null) {
					conn.close();
				}
			}
			catch(SQLException e3) {
				e3.printStackTrace();
			}
		}
	}
}
