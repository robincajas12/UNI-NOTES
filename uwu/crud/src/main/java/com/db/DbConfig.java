package com.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class DbConfig {
	private HikariDataSource ds;
	private HikariConfig config = new HikariConfig();
	{
		String url = "jdbc:sqlite:C:/Users/fing.labcom/Downloads/UNI-NOTES-main/uwu/crud/src/main/java/db.db";

		config.setJdbcUrl(url);
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
