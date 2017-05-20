package DataBase;
import java.sql.*;
				//Use Singleton design pattern to avoid duplicate of database connection
public class DataBase {
	
	private static DataBase instance;
	private Connection conn;
	private DataBase(){		
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn= DriverManager.getConnection("jdbc:mysql://localhost/data_users","root","");
			
		}
		catch(Exception e){
			
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
	public static DataBase getInstanca(){
		if(instance==null){instance=new DataBase();}
		return instance;
	}
	public ResultSet doSelect(String sql_querry){		
		
		try{
			
			Statement stm= conn.createStatement();
			ResultSet rs= stm.executeQuery(sql_querry);
			return rs;
		}
		catch(SQLException e){System.err.println("ERROR DataBase.doSelect: "+ e.getMessage()); return null;}
	}

	
}
