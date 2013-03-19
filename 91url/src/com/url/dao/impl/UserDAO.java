package com.url.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.jstl.sql.Result;

import com.url.bean.User;
import com.url.dao.IUserDAO;

public class UserDAO implements IUserDAO {
	private Connection conn=null;
	private ConnectionManager connectionManager = null;

	public UserDAO(){
		connectionManager = new ConnectionManager();
    }
	/*
	 * 增加一条用户信息,参数为包装好后的com.url.bean.User对象
	 */
	public int addUser(User user) {
		int[] result={0,0};
		List<Object> values = new ArrayList<Object>();
		
		values.add(user.getUsername());
		values.add(user.getPwd());
		values.add(user.getRegDate());
		values.add(0);
		String sqlValue = "INSERT INTO url_Basic_User(username,password,regDate,userType) VALUES (?,?,?,?)";
		
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
    /*
     * 删除一条用户信息,参数为对应的用户Id
     */
	public int deleteUser(int userId) {
		// TODO Auto-generated method stub
		return 0;
	}

    /*
     * 查询用户信息,参数为对应用的账号跟密码
     */
	public User getUserByNamePwd(String userName,String pwd) {
		User user = null;
		
		List<Object> values = new ArrayList<Object>();
		values.add(userName);
		values.add(pwd);

		
		String sqlValue = "{call proc_select_User(?,?)}";
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue(sqlValue);
		sqlCmdBean.setValues(values);
		try{
			Result result = sqlCmdBean.executeQuery();
			if(result.getRowCount()>=1){
				user = new User();
				Map row = result.getRows()[0];
				user.setUserId((Integer)row.get("userId"));
				user.setNickName((String)row.get("nickName"));
				user.setUserType((Integer)row.get("userType"));
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
		return user;
	}
	
	/**
	 * 根据用户ID查询其信息
	 */
    public User getUserByUserId(int userId){
        User user = null;
		List<Object> values = new ArrayList<Object>();
		values.add(userId);

		
		String sqlValue = "{call proc_select_ByUserID(?)}";
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue(sqlValue);
		sqlCmdBean.setValues(values);
		try{
			Result result = sqlCmdBean.executeQuery();
			if(result.getRowCount()>=1){
				user = new User();
				Map row = result.getRows()[0];
				user.setNickName((String)row.get("nickName"));
				user.setUserId((Integer)row.get("userId"));
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
		return user;
    }
    /*
     * 获取所有用户信息
     */
	 public List<User> getUserListByTagId(int tagId,String tagType){
		// TODO Auto-generated method stub
		return null;
	}
	
    /*
     * 更新一条用户信息,参数为包装好后的com.url.bean.User对象
     */
	public int updateUser(User user) {
		int[] result={};
		List<Object> values = new ArrayList<Object>();
		values.add(user.getNickName());
		values.add(user.getUserId());

		String sqlValue = "UPDATE url_Basic_User  SET nickName = ? WHERE userId = ?";
		
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
    
	/*
	 * 用户注册时检测用户名是否存在
	 */
	public boolean checkUserName(String username) {
		boolean result = false;
		List<Object> values = new ArrayList<Object>();
		values.add(username);
	
		String sqlValue = "SELECT userId FROM url_Basic_User WHERE username = ?";
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue(sqlValue);
		sqlCmdBean.setValues(values);
		try{
			Result r = sqlCmdBean.executeQuery();
			if(r.getRowCount()>=1){
				result = true;
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

    /**
     * 根据用户ID查询follow该用户的人
     * @param userId 用户ID
     * @return
     */
    public List<User> getFansByUserId(int userId,int pageNum){
    	List<User> users = new ArrayList<User>();
		List<Object> values = new ArrayList<Object>();
		values.add(userId);
		values.add(pageNum);

		
		String sqlValue = "{call proc_get_Fans(?,?)}";
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue(sqlValue);
		sqlCmdBean.setValues(values);
		try{
			Result result = sqlCmdBean.executeQuery();
			int counter = result.getRowCount();
			for(int i = 0 ; i < counter;i++){
				User user = new User();
				Map row = result.getRows()[i];
				user.setNickName((String)row.get("nickName"));
				user.setUserId((Integer)row.get("userId"));
				user.setRelationType((Integer)row.get("relationType"));
				users.add(user);
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
		return users;
    }
    
    /**
     * 根据用户ID查询该用户所关注的人
     * @param userId 用户ID
     * @return
     */
    public List<User> getFollowersByUserId(int userId,int pageNum){
    	List<User> users = new ArrayList<User>();
		List<Object> values = new ArrayList<Object>();
		values.add(userId);
		values.add(pageNum);
		
		String sqlValue = "{call proc_get_Followers(?,?)}";
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue(sqlValue);
		sqlCmdBean.setValues(values);
		try{
			Result result = sqlCmdBean.executeQuery();
			int counter = result.getRowCount();
			for(int i = 0 ; i < counter;i++){
				User user = new User();
				Map row = result.getRows()[i];
				user.setNickName((String)row.get("nickName"));
				user.setUserId((Integer)row.get("userId"));
				user.setRelationType((Integer)row.get("relationType"));
				users.add(user);
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
		return users;
    }
    
    /**
     * 根据用户ID查询关注该用户的人总数
     * @param userId
     * @return
     */
    public int getFansSum(int userId){
    	int sum = 0;
		List<Object> values = new ArrayList<Object>();
		values.add(userId);

		
		String sqlValue = "SELECT COUNT(*) AS SUM FROM url_User_Relation WHERE hostId = ?";
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue(sqlValue);
		sqlCmdBean.setValues(values);
		try{
			Result result = sqlCmdBean.executeQuery();
			if(result.getRowCount() >= 1){
				Map row = result.getRows()[0];
				sum = (Integer)row.get("SUM");
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
		return sum;
    }
    
    /**
     * 根据用户ID查询该用户关注的人的总数
     * @param userId
     * @return
     */
    public int getFollowersSum(int userId){
    	int sum = 0;
		List<Object> values = new ArrayList<Object>();
		values.add(userId);

		
		String sqlValue = "SELECT COUNT(*) AS SUM FROM url_User_Relation WHERE guestId = ?";
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue(sqlValue);
		sqlCmdBean.setValues(values);
		try{
			Result result = sqlCmdBean.executeQuery();
			if(result.getRowCount() >= 1){
				Map row = result.getRows()[0];
				sum = (Integer)row.get("SUM");
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
		return sum;
    }
    /**
     * 移除粉丝
     * @param userId 用户Id
     * @param guestId 粉丝ID
     * @return
     */
    public int removeFan(int userId,int guestId){
    	int[] result = {0,0};
		List<Object> values = new ArrayList<Object>();
		values.add(userId);
		values.add(guestId);
		
		String sqlValue = "{call proc_del_Fans(?,?)}";
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
     * 移除关注
     * @param userId 用户Id
     * @param hostId 被follow用户ID
     * @return
     */
    public int removeFollow(int userId,int hostId){
    	int[] result = {0,0};
		List<Object> values = new ArrayList<Object>();
		values.add(userId);
		values.add(hostId);
		
		String sqlValue = "{call proc_del_Follows(?,?)}";
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
     * 添加关注
     * @param userId 用户Id
     * @param hostId 被关注用户ID
     * @return
     */
    public int addFollow(int userId,int hostId){
    	int[] result = {0,0};
		List<Object> values = new ArrayList<Object>();
		values.add(userId);
		values.add(hostId);
		
		String sqlValue = "{call proc_add_Followers(?,?)}";
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
     * 查询是否存在关注关系
     * @param hostId 即将被关注的用户ID
     * @param guestId userId
     * @return
     */
    public boolean checkRelation(int hostId,int guestId){
    	boolean result = false;
		List<Object> values = new ArrayList<Object>();
		values.add(hostId);
		values.add(guestId);
		
		String sqlValue = "SELECT hostId FROM url_User_Relation WHERE hostId = ? AND guestId = ?";
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue(sqlValue);
		sqlCmdBean.setValues(values);
		try{
			Result r = sqlCmdBean.executeQuery();
			if(r.getRowCount() >= 1){
				result = true;
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
    /**
     * 根据tagId 获取url列表
     * @param tagId
     * @return
     */
    public List<User> getUserByTagId(int tagId, int pageNum){
    	List<User> users = new ArrayList<User>();
		List<Object> values = new ArrayList<Object>();
		values.add(tagId);
		values.add(pageNum);
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_getUserByUserTagId(?,?)}");
		sqlCmdBean.setValues(values);
		try{
			Result result = sqlCmdBean.executeQuery();
			int counter = result.getRowCount();
			for(int i = 0 ; i < counter;i++){
				User user = new User();
				Map row = result.getRows()[i];
				user.setUserId((Integer)row.get("userId"));
				user.setNickName((String)row.get("nickName"));
				users.add(user);
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
		return users;
    }
    /**
     * 获取对应标签的书签数量
     * @param tagId
     * @return
     */
    public int getUserByTagIdSum(int tagId){
    	int sum = 0;
		List<Object> values = new ArrayList<Object>();
		values.add(tagId);

		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("SELECT COUNT(*) AS URLSUM FROM url_User_UserTag  WHERE userTagId = ?");
		sqlCmdBean.setValues(values);
		try{
			Result r = sqlCmdBean.executeQuery();
			if(r.getRowCount() >= 1){
				Map row = r.getRows()[0];
				sum = (Integer)row.get("URLSUM");
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
		return sum;
    }
    /**
     * 根据输入的用户账号,将密码找回.
     * @param username 用户的邮箱
     * @return
     */
    public String getPwdByUserName(String username){
    	String result = "";
    	List<Object> values = new ArrayList<Object>();
		values.add(username);

		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("SELECT password FROM url_Basic_User WHERE username = ? ");
		sqlCmdBean.setValues(values);
		try{
			Result r = sqlCmdBean.executeQuery();
			if(r.getRowCount() >= 1){
				Map row = r.getRows()[0];
				result = (String)row.get("password");
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
    /**
     * 更改密码
     * @param username
     * @param exPwd
     * @param newPwd
     * @return
     */
    public int resetPwd(String username,String newPwd){
    	int result = -1;
    	List<Object> values = new ArrayList<Object>();
		values.add(newPwd);
		values.add(username);

		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("UPDATE url_Basic_User SET password = ? WHERE username = ? ");
		sqlCmdBean.setValues(values);
		try{
			result = sqlCmdBean.executeUpdate()[0];
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
