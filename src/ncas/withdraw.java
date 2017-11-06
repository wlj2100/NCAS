package ncas;

import java.sql.*;

/**
 * Created by whb on 2017/11/5.
 */
public class withdraw {
    private static final String JDBC_DRIVER = Client.JDBC_DRIVER;
    private static final String DB_URL = Client.DB_URL;
    private static final String USER = Client.USER;
    private static final String PASS = Client.PASS;

    private String student_id;
    public withdraw (String student_id){
        this.student_id = student_id;
    }
    public void drop () {
        withdraw a = new withdraw(student_id);
        a.show_available_course();
        String code = input.get("Please input the code of the course to be dropped:");
        String year = input.get("Please input the year of the course to be dropped:");
        String quarter = input.get("Please input the quarter of the course to be dropped:");
        if (a.check_access(code)){
            a.withdraw_sql(code, year, quarter);
            System.out.println("The course has been withdrawn!");
        }
        else {
            System.out.println("You don't have the access to withdraw the course!");
        }


    }
    public void show_available_course(){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();

            String sql;
            sql = "select t.UoSCode, u.UoSName, t.Year, t.Semester from (transcript t natural join unitofstudy u) where T.UoSCode = u.UoSCode and t.StudId = " + student_id + " and t.Grade is null";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String tmp = rs.getString("UoSCode");
                System.out.print(tmp);
                System.out.print("   ");
                tmp = rs.getString("Year");
                System.out.print(tmp);
                System.out.print("   ");
                tmp = rs.getString("Semester");
                System.out.print(tmp);
                System.out.print("   ");
                tmp = rs.getString("UoSName");
                System.out.print(tmp);
                System.out.println();
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch(Exception e1){
            e1.printStackTrace();
        }
        finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(Exception e2){
                System.out.println("statement closing error");
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(Exception e3){
                e3.printStackTrace();
            }
        }
    }

    public void withdraw_sql (String code, String year, String quarter) {
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();

            String transcript_sql, uosoffering_sql;
            transcript_sql = "delete from transcript where StudId = " + student_id + " and UoSCode = '" + code + "' and year = " + year + " and semester = '" + quarter + "'";
            uosoffering_sql = "update uosoffering set Enrollment = Enrollment - 1 where UoSCode = '" + code + "' and year = " + year + " and semester = '" + quarter + "'";
            System.out.println(transcript_sql);
            System.out.println(uosoffering_sql);
            PreparedStatement pstmt = conn.prepareStatement(transcript_sql);
            PreparedStatement pstmt2 = conn.prepareStatement(uosoffering_sql);
            pstmt.executeUpdate();
            pstmt2.executeUpdate();


            stmt.close();
            conn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(Exception e2){
                System.out.println("stmt closing error");
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException e3){
                e3.printStackTrace();
            }
        }
    }

    public boolean check_access (String code) {
        Connection conn = null;
        Statement stmt = null;
        boolean access = false;
        try{
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            stmt = conn.createStatement();
            String sql;
            sql = "SELECT UoSCode from transcript WHERE StudId = "  + student_id + " and Grade is null";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String row = rs.getString("UoSCode");
                if (code.equals(row)){
                    access = true;
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(Exception e2){
                System.out.println("stmt closing error");
            }
            try{
                if(conn!=null)
                    conn.close();
            }
            catch(SQLException e3){
                e3.printStackTrace();
            }
        }
        return access;
    }
}
