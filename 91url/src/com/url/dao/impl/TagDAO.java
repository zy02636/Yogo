package com.url.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.jstl.sql.Result;

import com.url.bean.Tag;
import com.url.dao.ITagDAO;

public class TagDAO implements ITagDAO {
	private Connection conn=null;
	private ConnectionManager connectionManager = null;

	TagDAO(){
		connectionManager = new ConnectionManager();
    }
	@Override
	public int[] addTag(Tag tag) {
		int[] result={0,0};
		List<Object> values = new ArrayList<Object>();
		
		values.add(tag.getTagName());
		values.add(tag.getUrlId());

		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_add_Url_Tag(?,?)}");
		sqlCmdBean.setValues(values);
		try{
			Result r = sqlCmdBean.executeQuery();
			if(r.getRowCount() >= 1){
				Map row = r.getRows()[0];
				//System.out.println("Rows Count: " + r.getRowCount());
				//result[0]返回的是受影响的行数
				//row.size():1的时候表示重复插入相同标签,2表示表同时插入数据成功,3表示超过限制标签数目
				if(row.size() == 2){
					result[0] = 1;
					result[1] = (Integer)row.get("tagId");
				}else if(row.size() == 1){
					result[0] = 0;
					result[1] = (Integer)row.get("tagId");
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
	public int delTagByTagUrlId(int tagId,int urlId) {
		int[] result={};
		List<Object> values = new ArrayList<Object>();
		
		values.add(tagId);
		values.add(urlId);
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_delete_Url_Tag(?,?)}");
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

	@Override
	public List<Tag> getTagsByUrlId(int urlId) {
		List<Tag> tags = new ArrayList<Tag>();
		List<Object> values = new ArrayList<Object>();
		values.add(urlId);

		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_getUrlTags(?)}");
		sqlCmdBean.setValues(values);
		try{
			Result result = sqlCmdBean.executeQuery();
			int counter = result.getRowCount();
			for(int i = 0 ; i < counter;i++){
				Tag tag = new Tag();
				Map row = result.getRows()[i];
				tag.setTagId((Integer)row.get("tagId"));
				tag.setTagName((String)row.get("tagName"));
				tag.setUrlId(urlId);
				tags.add(tag);
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
		return tags;
	}
    /*
     * 根据userId,获取该用户的书签的所有标签
     */
    public List<Tag> getTagsByUserId(int userId){
    	List<Tag> tags = new ArrayList<Tag>();
		List<Object> values = new ArrayList<Object>();
		values.add(userId);

		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_getUrlTagsByUser(?)}");
		sqlCmdBean.setValues(values);
		try{
			Result result = sqlCmdBean.executeQuery();
			int counter = result.getRowCount();
			for(int i = 0 ; i < counter;i++){
				Tag tag = new Tag();
				Map row = result.getRows()[i];
				tag.setTagId((Integer)row.get("tagId"));
				tag.setTagName((String)row.get("tagName"));
				tags.add(tag);
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
		return tags;
    }
	/**
	 * 根据tagPrefix 查询tag
	 */
	public List<Tag> getUrlTagsByTagPrefix(String tagPrefix){
		List<Tag> tags = new ArrayList<Tag>();
		List<Object> values = new ArrayList<Object>();
		values.add(tagPrefix);
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_selectUrlTag_Prefix(?)}");
		sqlCmdBean.setValues(values);
		try{
			Result result = sqlCmdBean.executeQuery();
			int counter = 0;
			if(result != null){
				counter = result.getRowCount();
			}
			for(int i = 0 ; i < counter;i++){
				Tag tag = new Tag();
				Map row = result.getRows()[i];
				tag.setTagName((String)row.get("tagName"));
				tags.add(tag);
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
		return tags;
	}
	/**
     * 根据标签ID获取标签名
     * @param userTagId
     * @return
     */
    public String getTagNameById(int tagId){
    	String result = ""; 
		List<Object> values = new ArrayList<Object>();
		values.add(tagId);

		
		String sqlValue = "SELECT tagName FROM url_Tag WHERE tagId = ?";
		
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
