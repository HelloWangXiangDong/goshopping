package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;

import java.util.List;

/**
 * Created by XDStation on 2016/8/8 0008.
 */
public interface SearchService {
    SearchResult search(String queryString, int page, int rows) throws Exception;
    TaotaoResult deleteSearch(List ids);
}
