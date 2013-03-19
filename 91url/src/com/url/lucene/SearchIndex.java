package com.url.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocCollector;


import com.url.bean.Url;
import com.url.lucene.configuration.IndexServletConfiguration;

public class SearchIndex{
	public static final String INDEX_STORE_PATH = IndexServletConfiguration.INDEX_STORE_PATH;//IndexServletConfiguration.INDEX_STORE_PATH;//Ӧ�����������ļ���
	/**
	 * ����urlId ���������������Ƶ�url
	 * @param urlId
	 * @return
	 */
    public List<Url> searchByUrlId(String shareId){
    	List<Url> urls = new ArrayList<Url>();
		MMAnalyzer analyzer = null;
		IndexSearcher indexSearcher = null;
    	try{
    		analyzer = new MMAnalyzer();
    		indexSearcher = new IndexSearcher(INDEX_STORE_PATH);//ʵ��������������
    		
    		 //��ѯ��һ��urlId
    		TopDocCollector urlCollector = new TopDocCollector(1);
    		//���ȸ���shareId��ѯ��url��featureOne �� featureTwo,��ֹ�ظ�����
    		QueryParser queryParser = new QueryParser("shareId",analyzer);
    		queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);
    	    Query query = queryParser.parse(shareId);
    		
    		indexSearcher.search(query, urlCollector);
    		ScoreDoc[] hits = urlCollector.topDocs().scoreDocs; 
    		if( hits.length >= 1 ){
	    		Document searchDoc =  indexSearcher.doc(hits[0].doc); //��ȡ��url�������е�����
	    		String featureOne = searchDoc.get("featureOne");
	    		
	    		List<Document> docList = new ArrayList<Document>();  //�洢document
	    		
	    		//������һ���в�ѯ
	    		TopDocCollector featureOneCollector = new TopDocCollector(15);
	    		//������һ�ַ������в�ѯ
	    		queryParser = new QueryParser("featureOne",analyzer);
	    		queryParser.setDefaultOperator(QueryParser.OR_OPERATOR);
	    		query = queryParser.parse(featureOne);
	    		
	    		indexSearcher.search(query, featureOneCollector);
	    		ScoreDoc[] featureOneHits = featureOneCollector.topDocs().scoreDocs; 
	    		
	    		//������һ��document����Ȩ�ؼ���,�����Document List
	    		for(ScoreDoc sdoc : featureOneHits){
	    			System.out.println("Lucene score: " + sdoc.score);
	    			sdoc.score = sdoc.score * (Float.parseFloat(indexSearcher.doc(sdoc.doc).get("scoreOne")));
	    			docList.add(indexSearcher.doc(sdoc.doc));
	    		}
	    		
	    		int docListSize = docList.size();
	    		for(int a = 0 ; a < docListSize ; a++){
	        		for(int b = a + 1; b < docListSize ; b++){
	        			if(featureOneHits[a].score < featureOneHits[b].score){
	        				Document temp = docList.get(a);
	        				docList.set(a, docList.get(b));
	        				docList.set(b, temp);
	        			}
	        		}
	        	}
	    		
	    		//ʵ����url����,����ӳɼ���
	    		for(Document doc : docList){
	                Url url = new Url();
	                url.setUrlId(Integer.parseInt(doc.get("urlId")));
	                url.setUrl(doc.get("url"));
	                url.setUrlTitle(doc.get("urlTitle"));
	                url.setAddDate(doc.get("addDate"));
	                url.setUrlOwner(doc.get("urlOwner"));
	                url.setUrlOwnerId(Integer.parseInt(doc.get("urlOwnerId")));
	                url.setShareId(Integer.parseInt(doc.get("shareId")));
	                url.setFeatureOne(doc.get("featureOne"));
	                url.setScoreOne(doc.get("scoreOne"));
	                urls.add(url);
	    		}
    		}
    	}catch(Exception ex){
    		System.out.println("���ɲ�ѯ���ʱ����: " + ex.getMessage());
   		    ex.printStackTrace();
    	}finally{
    		try{
    		    indexSearcher.close();
    		}catch(IOException ex){
    			System.out.println("�رղ�ѯ��ʱ����: " + ex.getMessage());
    		}
    	}
    	return urls;
    }
}
