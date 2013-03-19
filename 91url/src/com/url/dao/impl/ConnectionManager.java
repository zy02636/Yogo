package com.url.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class ConnectionManager {
	String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
	//String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=Url_91"; // 连接服务器和数据库sample
	//String userName = "sa"; // 默认用户名
	//String userPwd = "unnc2007"; // 密码
	private Connection dbConn = null;
	private Context ctx = null;
	
	public ConnectionManager(){
		
		try{
		   ctx = new InitialContext();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
    
	public Connection GetCon() {
		try {
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/91urlDB");
			dbConn = ds.getConnection();
			//Class.forName(driverName);
			//dbConn = DriverManager.getConnection(dbURL,userName,userPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbConn;
	}

	public void CloseCon() {
		try {
			if (!dbConn.isClosed()) {
				dbConn.close();
			}
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
	}
	public static void main(String[] args){
		ConnectionManager cm = new ConnectionManager();
		cm.GetCon();
	}
}
