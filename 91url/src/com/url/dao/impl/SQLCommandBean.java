package com.url.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

public class SQLCommandBean {
    private Connection conn;
    private String sqlValue;
    private List values;
    
    public void setConnection(Connection conn){
    	this.conn = conn;
    }
    
    public void setSqlValue(String sqlValue){
    	this.sqlValue = sqlValue;
    }
    
    public void setValues(List values){
    	this.values = values;
    }
    
    public Result executeQuery() throws SQLException{
    	Result result = null;
    	ResultSet rs = null;
    	
    	PreparedStatement pstmt = null;
    	Statement stmt = null;
    	try{
    		if(values != null && values.size() > 0){
    			String proc_Regx = "\\{call\\s*\\w+\\(\\)\\}";
    			String callStr = sqlValue;
    			Pattern pattern = Pattern.compile(proc_Regx);
    			callStr = callStr.replace("?","");
    			callStr = callStr.replace(",","");

    			Matcher m = pattern.matcher(callStr);
    			//判断是否为存储过程
    			if(m.matches()){
    				pstmt = conn.prepareCall(sqlValue);
    			}else{
    				pstmt = conn.prepareStatement(sqlValue);
    			}
    			setValues(pstmt,values);
    			rs = pstmt.executeQuery();
    		}else{
    			stmt = conn.createStatement();
    			rs = stmt.executeQuery(sqlValue);
    		}
    		result = ResultSupport.toResult(rs);
    	}finally{
    		if(rs!=null){
    			try{rs.close();}catch(SQLException e){}
    		}
    		if(stmt!=null){
    			try{stmt.close();}catch(SQLException e){}
    		}
    		if(pstmt!=null){
    			try{pstmt.close();}catch(SQLException e){}
    		}
    	}
    	return result;
    }
    
    /**
     * 第一个参数为受影响行数,第二个作为其他判断的参数
     * @return
     * @throws SQLException
     */
    public int[] executeUpdate() throws SQLException{
    	ResultSet rs = null;
    	PreparedStatement pstmt = null;
    	Statement stmt = null;
    	int[] result = {0,0};//第一个参数为受影响行数,第二个你需要的参数
    	try{
    		if(values != null && values.size() > 0){
    			String proc_Regx = "\\{call\\s*\\w+\\(\\)\\}"; //判断是否为存储过程
    			String callStr = sqlValue;
    			Pattern pattern = Pattern.compile(proc_Regx);
    			callStr = callStr.replace("?","");
    			callStr = callStr.replace(",","");

    			Matcher m = pattern.matcher(callStr);
    			if(m.matches()){
        			pstmt = conn.prepareCall(sqlValue);
    			}else{
        			pstmt = conn.prepareStatement(sqlValue);
    			}
    			
        		setValues(pstmt,values);
        		result[0] = pstmt.executeUpdate();
    		}else{
    			stmt = conn.createStatement();
    			result[0] = stmt.executeUpdate(sqlValue);
    		}
    	}finally{
    		if(rs!=null){
    			try{rs.close();}catch(SQLException e){}
    		}
    		if(stmt!=null){
    			try{stmt.close();}catch(SQLException e){}
    		}
    		if(pstmt!=null){
    			try{pstmt.close();}catch(SQLException e){}
    		}
    	}

    	return result;
    }
    
    private void setValues(PreparedStatement pstmt, List values)throws SQLException{
    	for(int i = 0; i < values.size();i++){
    		Object v = values.get(i);
    		pstmt.setObject(i+1, v);
    	}
    }
}
