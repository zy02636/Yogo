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
	public static final String INDEX_STORE_PATH = IndexServletConfiguration.INDEX_STORE_PATH;//应当存在配置文件中
	private UrlService urlService;
	
    public void createIndex(List<Url> urls){
		ApplicationContext oAC = new ClassPathXmlApplicationContext("applicationContext.xml");
		urlService = (UrlService) oAC.getBean("UrlService");
    	IndexWriter writer = null;
    	try{
    		//构建一个IndexWriter实例,对Field数目没有限制
    		writer = new IndexWriter(INDEX_STORE_PATH,new MMAnalyzer(),true,IndexWriter.MaxFieldLength.UNLIMITED);
    		writer.setMergeFactor(100);//设置一个segment里面存多少Document
    		writer.setMaxMergeDocs(100000);
    		writer.setUseCompoundFile(true);//将索引文件合并
    		writer.optimize();
             
    		int shareIdInfoId = 0;
    		for(Url url : urls){
        		Document urlDoc = new Document();
        		
        		//Field实例化属性介绍
        		//Store.No: 表示该Field不需要存储; Store.YES: 表示该Field需要存储;Store.COMPRESS: 表示用压缩方式存储索引
        		//Index.No: 表示该Field不需要索引,即不会被用户查找;Index.TOEKNIZED:会被分词索引化,即会被用户查找;Index.UN_TOKENIZED:不分词,但会被查找
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
        		
        		//将所有数据源添加到Document对象中
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
    		System.out.println("生成索引出错: " + ex.getMessage());
    	}finally{
    		try{
    			writer.close();
    		}catch(IOException ex){
        		System.out.println("关闭IndexWriter时出错: " + ex.getMessage());
        	}
    	}
    }
    
    public static void main(String[] args){
        
    	List<Url> urls = new ArrayList<Url>();
    	
    	Url url1 = new Url();
    	url1.setUrlId(1);
    	url1.setUrl("http://www.baidu.com");
    	url1.setUrlTitle("百度知道");
    	url1.setAddDate("2000-11-11");
    	url1.setUrlOwner("大饼");
    	url1.setUrlOwnerId(1);
    	url1.setShareId(1);
    	url1.setFeatureOne("你好 百度 引擎 搜索 网游 内容 社交网");
    	url1.setScoreOne("1");
    	
    	Url url2 = new Url();
    	url2.setUrlId(2);
    	url2.setUrl("http://www.baidu123.com");
    	url2.setUrlTitle("百度知道123");
    	url2.setAddDate("2000-11-11");
    	url2.setUrlOwner("大饼");
    	url2.setUrlOwnerId(1);
    	url2.setShareId(2);
    	url2.setFeatureOne("你好123 百度 引擎132 搜索 内容 社交网");
    	url2.setScoreOne("1");
    	
    	Url url3 = new Url();
    	url3.setUrlId(3);
    	url3.setUrl("http://www.baidu456.com");
    	url3.setUrlTitle("百度知道456");
    	url3.setAddDate("2000-11-11");
    	url3.setUrlOwner("大饼");
    	url3.setUrlOwnerId(1);
    	url3.setShareId(3);
    	url3.setFeatureOne("你好啊 百度123 发生的 犹太人 分散 内容 社交网");
    	url3.setScoreOne("1");
    	
    	Url url4 = new Url();
    	url4.setUrlId(4);
    	url4.setUrl("http://www.baidu789.com");
    	url4.setUrlTitle("百度知道789");
    	url4.setAddDate("2000-11-11");
    	url4.setUrlOwner("大饼");
    	url4.setUrlOwnerId(1);
    	url4.setShareId(4);
    	url4.setFeatureOne("你好 发生的 引擎 全额去 内容 犹太人 杯具 游戏");
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
    	delIndex.deleteIndex("百度");
    	
    }
}
