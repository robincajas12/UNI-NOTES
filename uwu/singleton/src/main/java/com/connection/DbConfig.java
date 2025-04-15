package com.connection;

import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection; 


public class DbConfig {
	private HikariDataSource ds;
	private HikariConfig config = new HikariConfig();
	{
		config.setJdbcUrl("jdbc:sqlite:db.db");
		config.setUsername("sa");
		config.setPassword("");
		config.setConnectionTimeout(3000);
		ds = new HikariDataSource(config);
	}
	public Connection getConnection()
	{
		try
		{
			System.out.println("Conectado uwu");
			return ds.getConnection();
			
		}catch(SQLException e)
		{
			throw new RuntimeException(e);
		}
	}
}
