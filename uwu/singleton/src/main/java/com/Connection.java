package com;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.hibernate.HikariConnectionProvider;

public class Connection {
	private HikariDataSource ds = null;
	private HikariConfig config = new HikariConfig();
	{
		config.setJdbcUrl("jdbc:sqlite:db.db");
		config.setUsername("sa");
		config.setPassword("");
		config.setConnectionTimeout(3000);
		ds = new HikariDataSource(config);
	}
	public static HikariConnectionProvider get()
	{
		return null;
	}
}
