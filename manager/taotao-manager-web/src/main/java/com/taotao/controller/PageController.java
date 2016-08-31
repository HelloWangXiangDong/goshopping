package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by XDStation on 2016/7/24 0024.
 */
@Controller
public class PageController {
    /**
     * 显示首页
     */
    @RequestMapping(value="/",method = RequestMethod.GET)
    public String showIndex(){
        return "index";
    }

    /**
     * 动态显示所有jsp页面
     * @param page
     * 页面参数
     * @return
     */
    @RequestMapping(value="/{page}", method = RequestMethod.GET)
    public String showPage(@PathVariable String page){
        return page;
    }
}
