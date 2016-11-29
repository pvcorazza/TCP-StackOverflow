package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnect{
	
	public void connect(String username,String pwd){
		Connection conn = null;
		try{
			
			String url1 = "jdbc:mysql://sql9.freesqldatabase.com:3306/sql9146901";
	        String user = "sql9146901";
	        String password = "tPcthmKbZt";
	        
	        conn = DriverManager.getConnection(url1, user, password);
	        if (conn != null) {
                System.out.println("Connected to the database FREESQLDATABASE");
            }
	        
			
		}
		
		catch(SQLException ex){
			ex.printStackTrace();
		}
		
		 try {
			Statement stmt = (Statement) conn.createStatement();
			
			String sqlInsert = "insert into user " // need a space
		               + "VALUES (NULL, '"+username+"', '"+pwd+"', '0', '1')";
		         System.out.println("The SQL query is: " + sqlInsert);  // Echo for debugging
		         int countInserted = stmt.executeUpdate(sqlInsert);
		         System.out.println(countInserted + " records inserted.\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
	}
}

