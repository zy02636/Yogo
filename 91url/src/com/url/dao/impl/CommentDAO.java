package com.url.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.jstl.sql.Result;

import com.url.bean.Comment;
import com.url.bean.Tag;
import com.url.dao.ICommentDAO;

public class CommentDAO implements ICommentDAO {
	private Connection conn=null;
	private ConnectionManager connectionManager = null;

	CommentDAO(){
		connectionManager = new ConnectionManager();
    }
	@Override
	public int addComment(Comment comment) {
		int[] result={0,0};
		List<Object> values = new ArrayList<Object>();
		
		values.add(comment.getUrlId());
		values.add(comment.getUserId());
		values.add(comment.getCommentContent());
		values.add(comment.getUserNickName());
		values.add(comment.getReplyToUserId());
		values.add(comment.getReplyToUserName());
		values.add(comment.getReplyDate());
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_add_Comment(?,?,?,?,?,?,?)}");
		sqlCmdBean.setValues(values);
		try{
			Result r = sqlCmdBean.executeQuery();
			if(r.getRowCount() >= 1){
				Map row = r.getRows()[0];
				//System.out.println("Rows Count: " + r.getRowCount());
				//result[0]返回的是受影响的行数.result[1]的参数用来区别不同状态
				if(row.size() == 1){
					result[0] = 1;
					result[1] = (Integer)row.get("commentId");
				}else{
					result[0] = 0;
					result[1] = 0;
				}
			}
		}catch(SQLException ex){
			ex.printStackTrace();
			return -1;
		}finally{
			try{
			    conn.close();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		return result[1];
	}

	@Override
	public int delCommentByCommentId(int commentId) {
		int[] result={0,0};
		List<Object> values = new ArrayList<Object>();
		
		values.add(commentId);
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_del_Comment(?)}");
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
	public List<Comment> getCommentsByUrlId(int urlId,int comPage) {
		List<Comment> comments = new ArrayList<Comment>();
		List<Object> values = new ArrayList<Object>();
		values.add(urlId);
		values.add(comPage);
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_select_Comment(?,?)}");
		sqlCmdBean.setValues(values);
		try{
			Result result = sqlCmdBean.executeQuery();
			int counter = result.getRowCount();
			for(int i = 0 ; i < counter;i++){
				Comment comment = new Comment();
				Map row = result.getRows()[i];
				comment.setCommentContent((String)row.get("commentContent"));
				comment.setCommentId((Integer)row.get("commentId"));
				comment.setUserId((Integer)row.get("userId"));
				comment.setUserNickName((String)row.get("userNickName"));
				comment.setReplyToUserId((Integer)row.get("replyToUserId"));
				comment.setReplyToUserName((String)row.get("replyToUserName"));
				comment.setReplyDate((String)row.get("replyDate"));
				comments.add(comment);
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
		return comments;
	}
	
	public int getCommentSumByUrlId(int urlId){
		int result = 0;
		List<Object> values = new ArrayList<Object>();
		values.add(urlId);
		
		conn = connectionManager.GetCon();
		
		SQLCommandBean sqlCmdBean = new SQLCommandBean();
		sqlCmdBean.setConnection(conn);
		sqlCmdBean.setSqlValue("{call proc_get_CommentSum(?)}");
		sqlCmdBean.setValues(values);
		try{
			Result r = sqlCmdBean.executeQuery();
			if(r.getRowCount() >= 1){
				Map row = r.getRows()[0];
				if(row.size() == 1){
					result = (Integer)row.get("commentSum");
				}else{
					result = 0;
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
}
