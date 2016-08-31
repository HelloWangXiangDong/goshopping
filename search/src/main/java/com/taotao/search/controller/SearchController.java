package com.taotao.search.controller;

import com.alibaba.druid.util.StringUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDStation on 2016/8/8 0008.
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value="/query",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult searchItem(
    @RequestParam("q") String queryString,
    @RequestParam(defaultValue = "1")Integer page,
    @RequestParam(defaultValue = "60")Integer rows){
        if(StringUtils.isEmpty(queryString)){
            return TaotaoResult.build(400,"查询条件不能为空");
        }
        SearchResult searchResult = null;
        try {
            queryString = new String(queryString.getBytes("iso8859-1"),"utf-8");
            searchResult = searchService.search(queryString,page,rows);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok(searchResult);
    }
    @RequestMapping("/delete/{ids}")
    @ResponseBody
    public TaotaoResult delete(@PathVariable String ids){
        List<String> list = new ArrayList<String>();
        String[] idss = ids.split(",");
        for(String s : idss){
            list.add(s);
        }
        searchService.deleteSearch(list);
        return TaotaoResult.ok();
    }

}
