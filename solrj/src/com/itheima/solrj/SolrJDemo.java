package com.itheima.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * solrj使用演示 
 * @author 李腾飞
 *
 */
public class SolrJDemo {

	/**
	 * 添加
	 * @throws Exception
	 */
	@Test
	public void testAdd() throws Exception {
		String baseURL = "http://localhost:8080/solr";
		SolrServer solrServer = new HttpSolrServer(baseURL);
		
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "001");
		doc.addField("name", "李四");
		
		solrServer.add(doc);
		solrServer.commit();
	}
	
	/**
	 * 删除
	 * @throws Exception
	 */
	@Test
	public void testDelete() throws Exception {
		String baseURL = "http://localhost:8080/solr";
		SolrServer solrServer = new HttpSolrServer(baseURL);
		
		// 根据id删除
		solrServer.deleteById("1");
		
		solrServer.commit();
	}
	
	/**
	 * 查询
	 * @throws Exception
	 */
	@Test
	public void testQuery() throws Exception {
		String baseURL = "http://localhost:8080/solr";
		SolrServer solrServer = new HttpSolrServer(baseURL);
		
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		
		QueryResponse response = solrServer.query(query);
		SolrDocumentList results = response.getResults();
		System.out.println("搜索到的结果总数：" + results.getNumFound());
		for (SolrDocument solrDocument : results) {
			System.out.println("----------------------");
			System.out.println("id:" + solrDocument.get("id"));
			System.out.println("name:" + solrDocument.get("name"));
		}
		
		solrServer.commit();
	}
	
}
