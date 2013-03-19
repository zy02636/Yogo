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
     * ���������ļ� ��ȡ �����Ĵ洢·�� ����ȡ���������� Tag
     */
    private void loadConfigFile(){
		try {
			ServletContext context = getServletContext();
			String filePathParameter = this.getInitParameter("storePath");
			if(filePathParameter == null){
				throw new Exception("web.xml������luceneConfig.xml�ļ�·������.");
			}
			String realFilePath = context.getRealPath(filePathParameter);
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
    		Document doc = docBuilder.parse(new File(realFilePath));
            doc.getDocumentElement().normalize();
            //��ȡ���ڵ�
            Element rootElement = doc.getDocumentElement();
            //��ȡ���ڵ�洢������·��ֵ
            INDEX_STORE_PATH = this.getServletContext().getRealPath("/") + ((Element)(rootElement.getElementsByTagName("indexStorePath").item(0))).getAttribute("path");
            if(INDEX_STORE_PATH == null){
            	throw new Exception("yogoIndexContent.xml�����ļ��������洢·��Ϊ��");
            }
        }
        catch (Exception ex) {
           System.out.println("��ȡ luceneConfig.xml �ļ����� ���������ļ�·��: " + ex.toString());
        }finally{
        	
        }
    } 
	public void init() throws ServletException {
		loadConfigFile();
	}
}
