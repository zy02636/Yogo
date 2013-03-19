package com.url.dao.impl;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.jstl.sql.Result;

import com.url.bean.Url;
import com.url.dao.IUrlDAO;

public class UrlDAO implements IUrlDAO {
	private Connection conn=null;
	private ConnectionManager connectionManager = null;

	public UrlDAO(){
		connectionManager = new ConnectionManager();
    }
	/*
	 * 添加一条Url信息,参数为封装好后的com.url.bean.Url 对象
	 */
	public int addUrl(Url url) {
		int result = -1;
		List<Object> values = new ArrayList<Object>();
		
		values.add(url.getUrl());
		values.add(url.getUrlTitle());
		values.add(url.getAddDate());
		values.add(url.getUrlOwner());
		values.add(url.getUrlOwnerId());
		values.add(url.getUrlType());
		conn = connectionManager.GetCon();

		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_add_Url(?,?,?,?,?,?)}");
		sqlCmdBean.setValues(values);
		try{
			Result r = sqlCmdBean.executeQuery();
			if(r.getRowCount() >= 1){
				Map row = r.getRows()[0];
			    result = (Integer)row.get("urlId");
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
	
    /*
     * 删除一条Url记录的接口方法,参数为对应Url的ID
     */
	public int deleteUrl(int urlId) {
		int[] result={0,0};
		List<Object> values = new ArrayList<Object>();
		values.add(urlId);

		String sqlValue = "{call proc_delUrl(?)}";
		
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
     * 更新一条Url记录的接口方法,参数为包装好后的com.url.bean.Url对象
     */
	public int updateUrl(Url updateUrl) {
		int[] result={0,0};
		List<Object> values = new ArrayList<Object>();
		values.add(updateUrl.getUrlId());
		values.add(updateUrl.getUrlTitle());
		
		String sqlValue = "{call proc_update_Url(?,?)}";
		
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
	 * 根据用户ID获取相应用户存储所有的Url信息,参数为对应用户的ID
	 */
	public List<Url> getUrlsByUserId(int userId,int pageNum,int queryType){
		List<Url> urls = new ArrayList<Url>();
		List<Object> values = new ArrayList<Object>();
		values.add(userId);
		values.add(pageNum);
		values.add(queryType);

		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_get_Url(?,?,?)}");
		sqlCmdBean.setValues(values);
		try{
			Result result = sqlCmdBean.executeQuery();
			int counter = result.getRowCount();
			for(int i = 0 ; i < counter;i++){
				Url url = new Url();
				Map row = result.getRows()[i];
				url.setAddDate(((String)row.get("addDate")));
				url.setUrl((String)row.get("url"));
				url.setUrlId((Integer)row.get("urlId"));
				url.setUrlOwner((String)row.get("urlOwner"));
				url.setUrlOwnerId((Integer)row.get("urlOwnerId"));
				url.setUrlTitle((String)row.get("urlTitle"));
				url.setUrlType((Integer)row.get("urlType"));
				url.setShareId((Integer)row.get("shareId"));
				urls.add(url);
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
		return urls;
	}
    
	/**
	 * 根据用户ID查询其所有好友对外公开的url记录
	 */
	public List<Url> getGuestUrlsByUserId(int userId,int pageNum){
		List<Url> urls = new ArrayList<Url>();
		List<Object> values = new ArrayList<Object>();
		values.add(userId);
		values.add(pageNum);
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call select_User_MainUrl(?,?)}");
		sqlCmdBean.setValues(values);
		try{
			Result result = sqlCmdBean.executeQuery();
			int counter = result.getRowCount();
			for(int i = 0 ; i < counter;i++){
				Url url = new Url();
				Map row = result.getRows()[i];
				url.setAddDate(((String)row.get("addDate")));
				url.setUrl((String)row.get("url"));
				url.setUrlId((Integer)row.get("urlId"));
				url.setUrlOwner((String)row.get("urlOwner"));
				url.setUrlOwnerId((Integer)row.get("urlOwnerId"));
				url.setUrlTitle((String)row.get("urlTitle"));
				url.setUrlType((Integer)row.get("urlType"));
				url.setShareId((Integer)row.get("shareId"));
				urls.add(url);
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
		return urls;
	}
	/*
	 * 根据用户ID获取相应用户存储所有的Url信息,参数为对应用户的ID
	 */
	public List<Url> getUrlsByUserIdAndIndex(int userId,int index){
		List<Url> urls = new ArrayList<Url>();
		List<Object> values = new ArrayList<Object>();
		values.add(index);
		values.add(userId);
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_getUrls_Page_Plugin(?,?)}");
		sqlCmdBean.setValues(values);
		try{
			Result result = sqlCmdBean.executeQuery();
			int counter = result.getRowCount();
			for(int i = 0 ; i < counter;i++){
				Url url = new Url();
				Map row = result.getRows()[i];
				url.setUrl((String)row.get("url"));
				url.setUrlTitle((String)row.get("urlTitle"));
				urls.add(url);
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
		return urls;
	}
	
	/**
	 * 根据shareId 更新url的分享数量
	 */
	public int[] shareUrl(int shareId,Url url){
		int[] result = {0,0};
		List<Object> values = new ArrayList<Object>();
		
		values.add(shareId);
		values.add(url.getAddDate());
		values.add(url.getUrlOwner());
		values.add(url.getUrlOwnerId());
		values.add(url.getUrlType());
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_share_Url(?,?,?,?,?)}");
		sqlCmdBean.setValues(values);
		try{
			Result r = sqlCmdBean.executeQuery();
			if(r.getRowCount() >= 1){
				Map row = r.getRows()[0];
				if(row.size() == 1){
					result[0] = 1;  //插入成功,分享url的数目直接用js修改就行
				}else{
					result[0] = 0;
				}
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
	
	public HashMap<String,Object> getUrlShareSum(int shareId){
		HashMap<String,Object> result = new HashMap<String,Object>();
		List<Object> values = new ArrayList<Object>();
		
		values.add(shareId);
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		//sqlCmdBean.setSqlValue("{call proc_get_Url_ShareSum(?)}");
		sqlCmdBean.setSqlValue("{call proc_get_Url_ShareSum(?)}");
		sqlCmdBean.setValues(values);
		try{
			Result r = sqlCmdBean.executeQuery();

			if(r.getRowCount() >= 1){
				Map row = r.getRows()[0];
				if(row.size() == 3){
					result.put("shareSum", (Integer)row.get("shareSum"));
					result.put("firstOwner", (String)row.get("firstOwner"));
					result.put("firstOwnerId", (Integer)row.get("firstOwnerId"));
				}
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
     * 根据用户ID查询用户分享的书签数目
     * @param userId
     * @return
     */
    public int getUrlSumByUserId(int userId){
    	int sum = 0;
		List<Object> values = new ArrayList<Object>();
		values.add(userId);

		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("SELECT COUNT(*) AS SUM FROM url_Url WHERE urlOwnerId = ?");
		sqlCmdBean.setValues(values);
		try{
			Result r = sqlCmdBean.executeQuery();
			if(r.getRowCount() >= 1){
				Map row = r.getRows()[0];
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
     * 获取关注用户的所发url数目
     * @param userId
     * @return
     */
    public int getFollowsUrlSum(int userId){
    	int sum = 0;
		List<Object> values = new ArrayList<Object>();
		values.add(userId);

		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call select_User_MainUrl_Sum(?)}");
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
     * 根据tagId获取Url
     * @param tagId 
     * @param pageNum
     * @param userId 当用户查询自己的书签时 值需要大于 0
     * @return
     */
    public List<Url> getUrlByTagId(int tagId, int pageNum,int userId){
    	List<Url> urls = new ArrayList<Url>();
		List<Object> values = new ArrayList<Object>();
		values.add(tagId);
		values.add(pageNum);
		values.add(userId);
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_getUrlByTagId(?,?,?)}");
		sqlCmdBean.setValues(values);
		try{
			Result result = sqlCmdBean.executeQuery();
			int counter = result.getRowCount();
			for(int i = 0 ; i < counter;i++){
				Url url = new Url();
				Map row = result.getRows()[i];
				url.setAddDate(((String)row.get("addDate")));
				url.setUrl((String)row.get("url"));
				url.setUrlId((Integer)row.get("urlId"));
				url.setUrlOwner((String)row.get("urlOwner"));
				url.setUrlOwnerId((Integer)row.get("urlOwnerId"));
				url.setUrlTitle((String)row.get("urlTitle"));
				url.setUrlType((Integer)row.get("urlType"));
				url.setShareId((Integer)row.get("shareId"));
				urls.add(url);
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
		return urls;
    }
    /**
     * 获取对应标签的书签数量
     * @param tagId
     * @return
     */
    public int getUrlByTagIdSum(int tagId){
    	int sum = 0;
		List<Object> values = new ArrayList<Object>();
		values.add(tagId);

		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("SELECT COUNT(*) AS URLSUM FROM url_Url_Tag  WHERE tagId = ?");
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
     * 根据urlId 查询规定大小的url组
     * @param groundCount
     * @param startUrlId
     * @return
     */
    public List<com.url.ws.bean.Url> getUrlGroupByStartUrlId(int groupCount,int firstShareId,int shareIdInfoId){
    	List<com.url.ws.bean.Url> urls = new ArrayList<com.url.ws.bean.Url>();
		List<Object> values = new ArrayList<Object>();
		values.add(groupCount);
		values.add(firstShareId);
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_getIndexUrl_ValidateByShareId(?,?)}");
		sqlCmdBean.setValues(values);
		try{
			Result result = sqlCmdBean.executeQuery();
			int counter = result.getRowCount();
			for(int i = 0 ; i < counter;i++){
				com.url.ws.bean.Url url = new com.url.ws.bean.Url();
				Map row = result.getRows()[i];
				url.setAddDate(((String)row.get("addDate")));
				url.setUrl((String)row.get("url"));
				url.setUrlId((Integer)row.get("urlId"));
				url.setUrlOwner((String)row.get("urlOwner"));
				url.setUrlOwnerId((Integer)row.get("urlOwnerId"));
				url.setUrlTitle((String)row.get("urlTitle"));
				url.setShareId((Integer)row.get("shareId"));
				url.setShareIdInfoId(shareIdInfoId);
				urls.add(url);
			}
			//在url_ShareId表中的grounpCount可能会比实际的返回url记录要大
			//因此需要在返回的时候再次对url_ShareId表中的grounpCount进行更新
		    values.clear();
		    values.add(urls.size());
		    values.add(shareIdInfoId);
		    sqlCmdBean.setSqlValue("UPDATE url_ShareId SET groupSum = ? WHERE id = ?");
		    sqlCmdBean.setValues(values);
		    sqlCmdBean.executeUpdate();
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			try{
			    conn.close();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		return urls;
    }
    /**
     * 当该条url被处理以后
     * @param urlId
     * @return
     */
    public int updateShareState(int lastShareId){
    	int[] result = {0,0};
		List<Object> values = new ArrayList<Object>();
		values.add(lastShareId);
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("UPDATE url_Share  SET indexed = 1 WHERE shareId = ?");
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
     * 获取share的indexed 状态
     * @param shareId
     * @return
     */
    public int getShareState(int shareId){
    	int result = 0;
		List<Object> values = new ArrayList<Object>();
		values.add(shareId);
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("SELECT indexed FROM url_Share WHERE shareId = ?");
		sqlCmdBean.setValues(values);
		try{
			Result rs = sqlCmdBean.executeQuery();
			if(rs.getRowCount() >= 1){
				Map row = rs.getRows()[0];
				result = (Integer)row.get("indexed");
			}else{
				result = 0;
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
     * 每次有一个程序通过Web Service访问获取信息时
     * 会有相应的记录.
     * @param requestUser 请求的用户
     * @param requestTime 请求的时间
     * @return 返回的为新增一条记录的id
     */
    public int addShareIdInfo(int firstShareId, int groupSum,String requestUser,String requestTime){
    	int[] result = {0,0};
    	List<Object> values = new ArrayList<Object>();
		values.add(firstShareId);
		values.add(groupSum);
		values.add(requestUser);
		values.add(requestTime);
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_addShareId(?,?,?,?)}");
		sqlCmdBean.setValues(values);
		try{
			Result rs = sqlCmdBean.executeQuery();
			Map row = rs.getRows()[0];
			result[0] = (Integer)row.get("id");
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		finally{
			try{
			    conn.close();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		return result[0];
    }
    /**
     * 当上传处理后的url信息成功后,更新shareId状态
     * @param id shareId表的identitier
     * @return
     */
    public int updateShareIdInfo(int id){
    	int[] result = {0,0};
    	List<Object> values = new ArrayList<Object>();
		values.add(id);

		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("UPDATE  url_ShareId SET state = 'done' WHERE id = ?");
		sqlCmdBean.setValues(values);
		try{
			result = sqlCmdBean.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		finally{
			try{
			    conn.close();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		return result[0];
    }
    /**
     * 获取最新一条用户通过web service获取url信息的记录
     * @return 返回最新一条记录的shareId值与groupCount的值,用于计算
     * 当前请求用户应该从多少shareId开始取得记录
     */
    public int[] getLastShareIdInfo(){
    	int[] result = {0,0};

		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("SELECT Top 1 firstShareId,groupSum FROM url_ShareId ORDER BY id DESC");
		try{
			Result rs = sqlCmdBean.executeQuery();
			int rowCounter = rs.getRowCount();
			if(rowCounter >= 1){
				Map row = rs.getRows()[0];
	            result[0] = (Integer)row.get("firstShareId");
	            result[1] = (Integer)row.get("groupSum");
			}else{
				result[0] = 0;
				result[1] = 0;
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
