package com.profiler.managers;

import java.sql.Connection;
import java.sql.SQLException;
import com.profiler.mysql.*;

public class MysqlManager {

	MySQL MySQL;
	Connection c;
	
	public MysqlManager(String username, String password, String host, String port, String database)
	{
		this.MySQL = new MySQL(host, port, database, username, password);
		
		try {
			c = MySQL.openConnection();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean isOpen()
	{
		try {
			return this.MySQL.checkConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public Connection getConnection()
	{
		return this.c;
	}
	
	public MySQL getMysql()
	{
		return this.MySQL;
	}
	
}
