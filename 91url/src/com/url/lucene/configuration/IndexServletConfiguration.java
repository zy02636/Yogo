package com.url.lucene.configuration;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class IndexServletConfiguration extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    public  static String INDEX_STORE_PATH;
    /**
     * 加载配置文件 读取 索引的存储路径 并获取所有索引的 Tag
     */
    private void loadConfigFile(){
		try {
			ServletContext context = getServletContext();
			String filePathParameter = this.getInitParameter("storePath");
			if(filePathParameter == null){
				throw new Exception("web.xml中配置luceneConfig.xml文件路径出错.");
			}
			String realFilePath = context.getRealPath(filePathParameter);
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
    		Document doc = docBuilder.parse(new File(realFilePath));
            doc.getDocumentElement().normalize();
            //读取根节点
            Element rootElement = doc.getDocumentElement();
            //读取根节点存储索引的路径值
            INDEX_STORE_PATH = this.getServletContext().getRealPath("/") + ((Element)(rootElement.getElementsByTagName("indexStorePath").item(0))).getAttribute("path");
            if(INDEX_STORE_PATH == null){
            	throw new Exception("yogoIndexContent.xml配置文件中索引存储路径为空");
            }
        }
        catch (Exception ex) {
           System.out.println("读取 luceneConfig.xml 文件出错 请检查配置文件路径: " + ex.toString());
        }finally{
        	
        }
    } 
	public void init() throws ServletException {
		loadConfigFile();
	}
}
