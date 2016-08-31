package com.taotao.portal.service;

import com.taotao.portal.pojo.SearchResult;

/**
 * Created by XDStation on 2016/8/9 0009.
 */
public interface SearchService {
    public SearchResult search(String queryString,Integer page);
}
