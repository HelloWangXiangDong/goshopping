package com.taotao.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by XDStation on 2016/8/8 0008.
 */
public class SolrTest {
    /**
     * solr添加数据
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void addDocument() throws IOException, SolrServerException {
        //创建连接
        SolrServer solrServer = new HttpSolrServer("http://192.168.153.24:8080/solr");
        //创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","100");
        document.addField("item_title","魅族手机");
        document.addField("item_sell_point","功能牛逼");
        document.addField("item_desc","颜值第一");
        document.addField("item_category_name","索尼大法好");
        ///添加对象到solr中
        solrServer.add(document);
        //提交
        solrServer.commit();
    }

    /**
     * solr删除
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void deleteDocument() throws IOException, SolrServerException {
        //创建连接
        SolrServer solrServer = new HttpSolrServer("http://192.168.153.24:8080/solr");
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }

    /**
     * 查询
     * @throws SolrServerException
     */
    @Test
    public void select() throws SolrServerException {
        //创建连接
        SolrServer solrServer = new HttpSolrServer("http://192.168.153.24:8080/solr");
        SolrQuery query = new SolrQuery();
        query.set("df","item_keywords");
        query.setQuery("魅族手机");
        QueryResponse query1 = solrServer.query(query);
        SolrDocumentList results = query1.getResults();
        for(SolrDocument d : results){
            System.out.println(d.get("item_title"));
        }

    }
}
