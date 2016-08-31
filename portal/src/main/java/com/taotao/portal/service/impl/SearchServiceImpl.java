package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by XDStation on 2016/8/9 0009.
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Value("#{configProperties['SEARCH_BASE_URL']}")
    private String SEARCH_BASE_URL;

    @Override
    public SearchResult search(String queryString,Integer page) {
        //调用taotao-search的服务
        Map<String,String> param = new HashMap<String,String>();
        param.put("q",queryString);
        param.put("page",String.valueOf(page));
        try {
            String response = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
            //将返回的结果转换为pojo
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(response, SearchResult.class);
            if(taotaoResult.getStatus() == 200){
                SearchResult searchResult = (SearchResult) taotaoResult.getData();
                return searchResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
