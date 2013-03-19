package com.url.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.url.bean.Url;
import com.url.lucene.configuration.IndexServletConfiguration;
import com.url.service.impl.UrlService;

public class CreateIndex {
	public static final String INDEX_STORE_PATH = IndexServletConfiguration.INDEX_STORE_PATH;//Ӧ�����������ļ���
	private UrlService urlService;
	
    public void createIndex(List<Url> urls){
		ApplicationContext oAC = new ClassPathXmlApplicationContext("applicationContext.xml");
		urlService = (UrlService) oAC.getBean("UrlService");
    	IndexWriter writer = null;
    	try{
    		//����һ��IndexWriterʵ��,��Field��Ŀû������
    		writer = new IndexWriter(INDEX_STORE_PATH,new MMAnalyzer(),true,IndexWriter.MaxFieldLength.UNLIMITED);
    		writer.setMergeFactor(100);//����һ��segment��������Document
    		writer.setMaxMergeDocs(100000);
    		writer.setUseCompoundFile(true);//�������ļ��ϲ�
    		writer.optimize();
             
    		int shareIdInfoId = 0;
    		for(Url url : urls){
        		Document urlDoc = new Document();
        		
        		//Fieldʵ�������Խ���
        		//Store.No: ��ʾ��Field����Ҫ�洢; Store.YES: ��ʾ��Field��Ҫ�洢;Store.COMPRESS: ��ʾ��ѹ����ʽ�洢����
        		//Index.No: ��ʾ��Field����Ҫ����,�����ᱻ�û�����;Index.TOEKNIZED:�ᱻ�ִ�������,���ᱻ�û�����;Index.UN_TOKENIZED:���ִ�,���ᱻ����
        		Field urlIdField = new Field("urlId",url.getUrlId()+"",Field.Store.YES,Field.Index.NO); 
        		Field urlField = new Field("url",url.getUrl(),Field.Store.YES,Field.Index.ANALYZED); 
        		Field urlTitleField = new Field("urlTitle",url.getUrlTitle(),Field.Store.YES,Field.Index.ANALYZED); 
        		Field addDateField = new Field("addDate",url.getAddDate(),Field.Store.YES,Field.Index.NO); 
        		Field urlOwnerField = new Field("urlOwner",url.getUrlOwner(),Field.Store.YES,Field.Index.NO); 
        		Field urlOwnerIdField = new Field("urlOwnerId",url.getUrlOwnerId()+"",Field.Store.YES,Field.Index.NO); 
        		Field shareIdField = new Field("shareId",url.getShareId()+"",Field.Store.YES,Field.Index.ANALYZED); 
        		Field shareIdInfoIdField = new Field("shareIdInfoId",url.getShareIdInfoId()+"",Field.Store.YES,Field.Index.ANALYZED); 
        		Field featureOneField = new Field("featureOne",url.getFeatureOne(),Field.Store.YES,Field.Index.ANALYZED);
        		Field scoreOneField = new Field("scoreOne",url.getScoreOne(),Field.Store.YES,Field.Index.NO);
        		
        		//����������Դ��ӵ�Document������
        		urlDoc.add(urlIdField);
        		urlDoc.add(urlField);
        		urlDoc.add(urlTitleField);
        		urlDoc.add(addDateField);
        		urlDoc.add(urlOwnerField);
        		urlDoc.add(urlOwnerIdField);
        		urlDoc.add(shareIdField);
        		urlDoc.add(shareIdInfoIdField);
        		urlDoc.add(featureOneField);
        		urlDoc.add(scoreOneField);
        		
        	    writer.addDocument(urlDoc);
        	    
        	    shareIdInfoId = url.getShareIdInfoId();
        	    
        	    urlService.updateShareState(url.getShareId());
    		}
    		urlService.updateShareIdInfo(shareIdInfoId);
    	}catch(IOException ex){
    		System.out.println("������������: " + ex.getMessage());
    	}finally{
    		try{
    			writer.close();
    		}catch(IOException ex){
        		System.out.println("�ر�IndexWriterʱ����: " + ex.getMessage());
        	}
    	}
    }
    
    public static void main(String[] args){
        
    	List<Url> urls = new ArrayList<Url>();
    	
    	Url url1 = new Url();
    	url1.setUrlId(1);
    	url1.setUrl("http://www.baidu.com");
    	url1.setUrlTitle("�ٶ�֪��");
    	url1.setAddDate("2000-11-11");
    	url1.setUrlOwner("���");
    	url1.setUrlOwnerId(1);
    	url1.setShareId(1);
    	url1.setFeatureOne("��� �ٶ� ���� ���� ���� ���� �罻��");
    	url1.setScoreOne("1");
    	
    	Url url2 = new Url();
    	url2.setUrlId(2);
    	url2.setUrl("http://www.baidu123.com");
    	url2.setUrlTitle("�ٶ�֪��123");
    	url2.setAddDate("2000-11-11");
    	url2.setUrlOwner("���");
    	url2.setUrlOwnerId(1);
    	url2.setShareId(2);
    	url2.setFeatureOne("���123 �ٶ� ����132 ���� ���� �罻��");
    	url2.setScoreOne("1");
    	
    	Url url3 = new Url();
    	url3.setUrlId(3);
    	url3.setUrl("http://www.baidu456.com");
    	url3.setUrlTitle("�ٶ�֪��456");
    	url3.setAddDate("2000-11-11");
    	url3.setUrlOwner("���");
    	url3.setUrlOwnerId(1);
    	url3.setShareId(3);
    	url3.setFeatureOne("��ð� �ٶ�123 ������ ��̫�� ��ɢ ���� �罻��");
    	url3.setScoreOne("1");
    	
    	Url url4 = new Url();
    	url4.setUrlId(4);
    	url4.setUrl("http://www.baidu789.com");
    	url4.setUrlTitle("�ٶ�֪��789");
    	url4.setAddDate("2000-11-11");
    	url4.setUrlOwner("���");
    	url4.setUrlOwnerId(1);
    	url4.setShareId(4);
    	url4.setFeatureOne("��� ������ ���� ȫ��ȥ ���� ��̫�� ���� ��Ϸ");
    	url4.setScoreOne("1");
    	
    	urls.add(url1);
    	urls.add(url2);
    	urls.add(url3);
    	urls.add(url4);
    	
    	CreateIndex createIndex = new CreateIndex();
    	createIndex.createIndex(urls);
    	
    	
    	SearchIndex s = new SearchIndex();
    	List<Url> result = s.searchByUrlId("3");
    	
    	for(Url url : result){
    		System.out.println(url.getFeatureOne() + " : " + url.getScoreOne());
    	}
    	
    	DeleteIndex delIndex = new DeleteIndex();
    	delIndex.deleteIndex("�ٶ�");
    	
    }
}
