package com.url.lucene;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;

import com.url.lucene.configuration.IndexServletConfiguration;


public class DeleteIndex {
	public static final String INDEX_STORE_PATH = IndexServletConfiguration.INDEX_STORE_PATH;//IndexServletConfiguration.INDEX_STORE_PATH;//Ӧ�����������ļ���
	/**
	 * @param storePath �����洢·��
	 * @param docId �ĵ�ID
	 */
	public void deleteIndex(String deleteTerm){
		IndexReader indexReader = null;
    	try{
            Term delTerm = new Term("featureOne",deleteTerm);
    	    
    	    indexReader = IndexReader.open(INDEX_STORE_PATH);
    	    indexReader.deleteDocuments(delTerm);
    	}catch(Exception ex){
    		System.out.println("org.yogo.lucene.index.DeleteIndex ɾ������ʱ����: "+ex.toString());
    	}finally{
    		try{
        		indexReader.close();
    		}catch(Exception ex){
    			ex.printStackTrace();
    			System.out.println("ɾ����������" + ex.getMessage());
    		}
    	}
    }
}
