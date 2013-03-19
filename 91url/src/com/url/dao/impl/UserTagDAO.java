package com.url.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.jstl.sql.Result;

import com.url.bean.UserTag;
import com.url.dao.IUserTagDAO;

public class UserTagDAO implements IUserTagDAO {
	private Connection conn=null;
	private ConnectionManager connectionManager = null;
	
	UserTagDAO(){
		connectionManager = new ConnectionManager();
    }
	/**
	 * 
	 */
	public int[] addUserTag(UserTag userTag){
		int[] result={0,0}; //第一个变量为受影响行数,第二个则为存储过程的返回值
		List<Object> values = new ArrayList<Object>();
		
		values.add(userTag.getUserId());
		values.add(userTag.getUserTagName());

		
		String sqlValue = "{call add_User_Tags(?,?)}";
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue(sqlValue);
		sqlCmdBean.setValues(values);
		try{
			Result r = sqlCmdBean.executeQuery();
			if(r.getRowCount() >= 1){
				Map row = r.getRows()[0];
				//System.out.println("Rows Count: " + r.getRowCount());
				//result[0]返回的是受影响的行数.result[1]的参数用来区别不同状态
				//row.size():1的时候表示重复插入相同标签,2表示表同时插入数据成功,3表示超过限制标签数目;
				if(row.size() == 2){
					result[0] = 1;
					result[1] = (Integer)row.get("userTagId");
				}else if(row.size() == 1){
					result[0] = 0;
					result[1] = (Integer)row.get("userTagId");
				}else if(row.size() == 3){
					result[0] = 0;
					result[1] = -1;
				}
			}
			//System.out.print("userTagId: "+result[1]);
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			try{
			    conn.close();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	@Override
	public List<UserTag> getUserTagsByUserId(int userId) {
		List<UserTag> userTags = new ArrayList<UserTag>();
		List<Object> values = new ArrayList<Object>();
		values.add(userId);
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_select_User_Tags(?)}");
		sqlCmdBean.setValues(values);
		try{
			Result result = sqlCmdBean.executeQuery();
			int counter = result.getRowCount();
			for(int i = 0 ; i < counter;i++){
				UserTag userTag = new UserTag();
				Map row = result.getRows()[i];
				userTag.setUserTagId((Integer)row.get("userTagId"));
				userTag.setUserTagName((String)row.get("tagName"));
				userTags.add(userTag);
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			try{
			    conn.close();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		return userTags;
	}
	
	/**
	 * 根据tagPrefix 查询tag
	 */
	public List<UserTag> getUserTagsByTagPrefix(String tagPrefix){
		List<UserTag> userTags = new ArrayList<UserTag>();
		List<Object> values = new ArrayList<Object>();
		values.add(tagPrefix);
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_selectUserTag_Prefix(?)}");
		sqlCmdBean.setValues(values);
		try{
			Result result = sqlCmdBean.executeQuery();
			int counter = result.getRowCount();
			for(int i = 0 ; i < counter;i++){
				UserTag userTag = new UserTag();
				Map row = result.getRows()[i];
				userTag.setUserTagName((String)row.get("tagName"));
				userTags.add(userTag);
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			try{
			    conn.close();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		return userTags;
	}
	public int deleteUserTagByUserId(int userTagId,int userId){
		int[] result={0,0};
		List<Object> values = new ArrayList<Object>();
		
		values.add(userTagId);
		values.add(userId);

		String sqlValue = "{call proc_del_User_Tags_Relation(?,?)}";
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue(sqlValue);
		sqlCmdBean.setValues(values);
		try{
			result = sqlCmdBean.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			try{
			    conn.close();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		return result[0];
	 }
    /**
     * 根据用户标签ID获取标签名
     * @param userTagId
     * @return
     */
    public String getUserTagNameById(int userTagId){
    	String result = ""; 
		List<Object> values = new ArrayList<Object>();
		
		values.add(userTagId);
		
		String sqlValue = "SELECT tagName FROM url_UserTag WHERE userTagId = ?";
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue(sqlValue);
		sqlCmdBean.setValues(values);
		try{
			Result r = sqlCmdBean.executeQuery();
			if(r.getRowCount() >=1){
			    Map row = r.getRows()[0];
			    result = (String)row.get("tagName");
			}
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			try{
			    conn.close();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		return result;
    }
}
