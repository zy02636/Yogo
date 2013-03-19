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
	 * ����һ���û���Ϣ,����Ϊ��װ�ú��com.url.bean.User����
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
     * ɾ��һ���û���Ϣ,����Ϊ��Ӧ���û�Id
     */
	public int deleteUser(int userId) {
		// TODO Auto-generated method stub
		return 0;
	}

    /*
     * ��ѯ�û���Ϣ,����Ϊ��Ӧ�õ��˺Ÿ�����
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
	 * �����û�ID��ѯ����Ϣ
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
     * ��ȡ�����û���Ϣ
     */
	 public List<User> getUserListByTagId(int tagId,String tagType){
		// TODO Auto-generated method stub
		return null;
	}
	
    /*
     * ����һ���û���Ϣ,����Ϊ��װ�ú��com.url.bean.User����
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
	 * �û�ע��ʱ����û����Ƿ����
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
     * �����û�ID��ѯfollow���û�����
     * @param userId �û�ID
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
     * �����û�ID��ѯ���û�����ע����
     * @param userId �û�ID
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
     * �����û�ID��ѯ��ע���û���������
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
     * �����û�ID��ѯ���û���ע���˵�����
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
     * �Ƴ���˿
     * @param userId �û�Id
     * @param guestId ��˿ID
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
     * �Ƴ���ע
     * @param userId �û�Id
     * @param hostId ��follow�û�ID
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
     * ��ӹ�ע
     * @param userId �û�Id
     * @param hostId ����ע�û�ID
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
     * ��ѯ�Ƿ���ڹ�ע��ϵ
     * @param hostId ��������ע���û�ID
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
     * ����tagId ��ȡurl�б�
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
     * ��ȡ��Ӧ��ǩ����ǩ����
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
     * ����������û��˺�,�������һ�.
     * @param username �û�������
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
     * ��������
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
