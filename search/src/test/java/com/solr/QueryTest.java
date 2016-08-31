package com.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

/**
 * Created by XDStation on 2016/8/8 0008.
 */
public class QueryTest {

    /**
     * Solr查询测试
     * @throws SolrServerException
     */
    @Test
    public void testSolrQuery() throws SolrServerException {
        //创建客户端服务器
        SolrServer solrServer = new HttpSolrServer("http://192.168.153.24:8080/solr");
        //创建查询条件
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.set("item_title","手机");
        query.setStart(1);
        query.setRows(20);
        //取查询结果
        QueryResponse response = solrServer.query(query);
        SolrDocumentList results = response.getResults();
        System.out.println("共查询到数据条数："+results.getNumFound());
        for(SolrDocument document : results){
            System.out.println(document.get("id"));
            System.out.println(document.get("item_title"));
        }
    }
}
