package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MySQLConnect{
	
	public void connect(String username,String pwd){
		Connection conn = null;
		try{
			
			String url1 = "jdbc:mysql://localhost:3306/stackoverflow";
	        String user = "root";
	        String password = "";
	        
	        conn = DriverManager.getConnection(url1, user, password);
	        if (conn != null) {
                System.out.println("Connected to the database test1");
            }
	        
			
		}
		
		catch(SQLException ex){
			ex.printStackTrace();
		}
		
		 try {
			Statement stmt = (Statement) conn.createStatement();
			
			String sqlInsert = "insert into user " // need a space
		               + "VALUES (NULL, 'show', 'qweqeqw', '0', '1')";
		         System.out.println("The SQL query is: " + sqlInsert);  // Echo for debugging
		         int countInserted = stmt.executeUpdate(sqlInsert);
		         System.out.println(countInserted + " records inserted.\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
	}
}

