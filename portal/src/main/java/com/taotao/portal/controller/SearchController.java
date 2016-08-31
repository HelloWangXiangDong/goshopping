package com.taotao.portal.controller;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * Created by XDStation on 2016/8/9 0009.
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String search(@RequestParam(value = "q") String queryString, @RequestParam(defaultValue = "1") Integer page , Model model){
        try {
            queryString = new String(queryString.getBytes("iso8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SearchResult search = searchService.search(queryString, page);
        model.addAttribute("query",queryString);
        model.addAttribute("totalPages",search.getPageCount());
        model.addAttribute("itemList",search.getItemList());
        model.addAttribute("page",search.getCurPage());
        return "search";
    }
}
