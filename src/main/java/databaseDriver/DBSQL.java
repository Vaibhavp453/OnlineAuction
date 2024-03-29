package databaseDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBSQL 
{
	public Connection connection;
	
	public String host=null;
	public Integer port=3306;
	public String database="Accounts";
	public String sqlUID="root";
	public String sqlPID="root";
	
	/*public static void main(String[] args)
	{
		DBSQL sql=new DBSQL();
		
		String query="SELECT * FROM endUsers";
		
		ArrayList<Object[]> data=sql.select(query);
		
		for(int i=0; i<data.size(); i++)
		{
			for(int j=0; j<data.get(i).length; j++)
			{
				System.out.println(data.get(i)[j]);
			}
		}
	}*/
	
	public DBSQL()
	{		
		connection=getConnection();
	}
	
	public void close()
	{
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void containsEntry()
	{
		
	}
	
	//Wont delete an entry, but will make an entry invalid
	public void delete()
	{
		
	}
	
	public Connection getConnection()
	{
		Connection dbConnection=null;
		
		String connectionString="jdbc:mysql:";
		
		if(host==null)
		{
			connectionString+="//localhost";
		}
		else
		{
			connectionString+="//"+host;
		}
		
		if(port!=null)
		{
			connectionString+=":"+String.valueOf(port);
		}
		
		if(database!=null)
		{
			connectionString+="/"+database;
		}
		
		//In case connection uses unicode. Unlikely, but no harm
		connectionString+="?autoReconnect=true&useUnicode=true&"
				+ "characterEncoding=UTF8";
		
		//Load the driver
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		}catch(ClassNotFoundException ex)
        {
        	ex.printStackTrace();
        } catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			dbConnection=DriverManager.getConnection
					(connectionString, sqlUID, sqlPID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dbConnection;
	}
	
	public void insert()
	{
		
	}
	
	/*public void refreshConnection()
	{
		if(!db_connection.isValid(10))

        {

          connectToServer();



          if(!db_connection.isValid(10))

            throw new Exception("Connection is not Valid");

        }
	}*/
	
	public ArrayList<Object[]> select(String query)
	{
		Statement statement;
		
		int colCount;
		
		ArrayList<Object[]> data=new ArrayList<Object[]>();
		Object[] row;
		
		try
		{
			statement=connection.createStatement();
			
			ResultSet rs=statement.executeQuery(query);
			
			try
			{
				colCount=rs.getMetaData().getColumnCount();
				
				while(rs.next())
				{
					if(rs.getMetaData().getColumnCount()!=colCount)
					{
						throw new Exception("Wrong # of Columns: "
					+colCount+" v.s. RS="
								+rs.getMetaData().getColumnCount());
					}
					
					row=new Object[colCount];
					
					for(int i=0; i<colCount; i++)
					{
						row[i]=rs.getObject(i+1);
					}
					
					data.add(row);
				}
			}finally
			{
				try
				{
					rs.close();
				}catch(Exception ignore) {}
			}
			
			statement.close();
		}catch(SQLSyntaxErrorException ex)
		{
			System.out.println("SQL Syntax Error");
			ex.printStackTrace();
		}catch(SQLException ex)
		{
			System.out.println("SQL Exception error");
			ex.printStackTrace();
		}catch(Exception ex)
		{
			System.out.println("Uncaught Exception: "+ex.getClass());
			ex.printStackTrace();
		}
		
		return data;
	}
	
	public void verifyCredentials()
	{
		
	}
}
