package eventable.pkgfor.students;

//import oracle.jdbc.OracleDriver;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBController {
    
    public static Connection conn;
    protected String currentQuery;
    public static Statement statement;
      
    //Open database connection
   public static Statement openConnection() {
        if (conn == null) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            }
                catch (ClassNotFoundException ex) {
                Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                conn = DriverManager.getConnection("jdbc:oracle:thin:@sage.business.unsw.edu.au:1521:orcl01","Y17GROUP214","zanyheart47");
                statement = conn.createStatement();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            } 
        }
        return statement;
    }

    //Close database connection
    public static void closeConnection(Connection conn, ResultSet rs, Statement statement) {
    try {
        rs.close();
        statement.close();
        conn.close();
    }
     catch (Exception ex) {
        ex.printStackTrace();
        }
    }

    //Takes in SQL Statement and returns Result Set
    public ResultSet getResultSet(String sqlstatement) throws SQLException, ClassNotFoundException {
        openConnection();
        java.sql.Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sqlstatement);
        return rs;
    }

    /*
    public static void performExecuteQuery(String query) {
        ResultSet rs=stmt.executeQuery(query);
        query.split(",")
        String[] columns = query.split(",");
        for (int i = 0; i < columnArray.length;i++){

        }  
        while(rs.next()) {
            System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
        }
    }*/

    /*public String returnName(int userID){
        java.sql.Statement statement = null;
        currentQuery = "SELECT FIRST_NAME || " " || LAST_NAME FROM APP_USER WHERE ID = " + Integer.toString(userID);
        openConnection();
        try {
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(currentQuery);
            if (rs.next()){
                statement.close();
                conn.commit();
                return rs.getString("GENDER");
            }
            else {
                statement.close();
                conn.commit();
                return "NOTHING FOUND";
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
            return "ERROR";
        }
    }*/
    
    //check special characters
    /*public boolean sanitise(String username, String password){
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m1 = p.matcher(username);
        Matcher m2 = p.matcher(password);
        boolean b1 = m1.find();
        boolean b2 = m2.find();
        if (b1 || b2){
            return false;
        }
        return true;
    }
    
    //Authenticate
    public boolean authenticate(String username, String password){//, Boolean staff){
        java.sql.Statement statement = null;
        currentQuery = "SELECT EMAIL FROM APP_USER WHERE EMAIL = '" + username + "' AND PASSWORD = '" + password + "'";
        openConnection();
        try {
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(currentQuery);
            if (rs.next()){
                statement.close();
                conn.commit();
                return true;
            }
            else {
                statement.close();
                conn.commit();
                return false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
   
    public void Insert(String insertSQL) {
        java.sql.Statement statement = null;
        openConnection();
        try {
            statement = conn.createStatement();
            statement.executeUpdate(insertSQL);
            statement.close();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public String returnSingleQuery(String query){
        java.sql.Statement statement = null;
        currentQuery = query;
        openConnection();
        try {
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(currentQuery);
            ColumnValue = ResultSet.getString(rs);
            System.out.print(ColumnValue);
            
            if (rs.next()){ 
                conn.commit();
                return rs.getString("ANSWER");
            }
            else {
                statement.close();
                conn.commit();
                return "NOTHING FOUND";
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
            return "ERROR";
        }
    }*/
}