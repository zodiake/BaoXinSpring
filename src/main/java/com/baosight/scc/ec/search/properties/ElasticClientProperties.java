/**
 * 
 */
package com.baosight.scc.ec.search.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.PropertyResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;



/**
 * @author lizidi@baosight
 *         

 */
public class ElasticClientProperties {



	private String clusterName;
	private String[] serverAddress;
	private String highlightTagPrefix;
	private String highlightTagSuffix;
	private String indexName;


    
    public ElasticClientProperties(String fileName) throws IOException, DocumentException{
    	initProperties(fileName);
    }
	

	public String getClusterName() {
		return clusterName;
	}


	public String[] getServerAddress() {
		return serverAddress;
	}

	



	public String getHighlightTagPrefix() {
		return highlightTagPrefix;
	}

	
	public String getHighlightTagSuffix() {
		return highlightTagSuffix;
	}

	

	
	
	public String getIndexName() {
		return indexName;
	}


	


	public synchronized  void initProperties(String fileName) throws IOException, DocumentException {
		String str=null;
		 SAXReader saxReader = new SAXReader();  
	     Document doc = saxReader.read(ElasticClientProperties.class.getClassLoader().getResourceAsStream(fileName));  
	     Element root = doc.getRootElement();//获取根元素

		this.clusterName = root.element(EsClientKey.clusterName).getTextTrim();
		this.indexName = root.element(EsClientKey.indexName).getTextTrim();
		String tag=root.element(EsClientKey.highlightTag).getTextTrim();
		this.highlightTagPrefix = tag.split(",")[0];
		this.highlightTagSuffix =tag.split(",")[1];
		if(!StringUtils.isEmpty((str=root.element(EsClientKey.serverAddress).getTextTrim()))){
			this.serverAddress=str.split(" ");
		}

	}

	private abstract class EsClientKey {
		/**
		 * 集群名字
		 */
		public static final String clusterName = "clusterName";
		/**
		 * index名字
		 */
		public static final String indexName = "indexName";
		
		/**
		 * 服务器地址
		 */
		public static final String serverAddress="serverAddress";
		
		/**
		 * 高亮CSS标签
		 */
		public static final String highlightTag="highlightTag";
		
		
	}


}
