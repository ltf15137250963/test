package com.itheima.solrj;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
 * solr的复杂查询
 * @author 李腾飞
 *
 */
public class SolrComplexQuery {

	public static void main(String[] args) throws SolrServerException {
		// 创建连接
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		// 创建一个query对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery("product_name:钻石");
		// 过滤条件
		query.setFilterQueries("product_price:[10 TO 20]");
		// 排序条件
		query.setSort("product_price", ORDER.asc);
		// 分页处理
		query.setStart(0);
		query.setRows(10);
		// 结果中域的列表
		query.setFields("id","product_name","product_catalog_name","product_price","product_picture");
		// 设置默认搜索域
		query.set("df", "product_keywords");
		// 高亮显示
		query.setHighlight(true);
		// 高亮显示的域
		query.addHighlightField("product_name");
		// 高亮显示的前缀
		query.setHighlightSimplePre("<em style='color:red'>");
		// 高亮显示的后缀
		query.setHighlightSimplePost("</em>");
		// 执行查询
		QueryResponse queryResponse = solrServer.query(query);
		// 获取查询结果
		SolrDocumentList results = queryResponse.getResults();
		// 查询结果数
		System.out.println("共查询到商品的数量:" + results.getNumFound());
		// 遍历结果集
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			// 取高亮显示
			String productName = "";
			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("product_name");
			// 判断是否有高亮
			if (list != null) {
				productName = list.get(0);
			} else {
				productName = (String) solrDocument.get("product_name");
			}
			System.out.println(productName);
			System.out.println(solrDocument.get("product_price"));
			System.out.println(solrDocument.get("product_catalog_name"));
			System.out.println(solrDocument.get("product_picture"));
		}
	}
	
}
