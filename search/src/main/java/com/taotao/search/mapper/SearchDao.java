package com.taotao.search.mapper;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;

/**
 * Created by XDStation on 2016/8/8 0008.
 */
public interface SearchDao {
    SearchResult search(SolrQuery query) throws Exception;
    void delete(List ids) throws IOException, SolrServerException;
}
