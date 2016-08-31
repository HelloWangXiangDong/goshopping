package com.taotao.rest.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by XDStation on 2016/8/7 0007.
 */
@Controller
@RequestMapping("/cache/sync")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/content/{contentId}")
    @ResponseBody
    public TaotaoResult contentSync(@PathVariable long contentId){
        TaotaoResult taotaoResult = redisService.deleteADcache(contentId);
        return taotaoResult;
    }

    @RequestMapping("/itemCat")
    @ResponseBody
    public TaotaoResult itemCatSync(){
        return redisService.deleteItemCatCache();
    }

    @RequestMapping("/delete/{itemId}")
    @ResponseBody
    public TaotaoResult deleteItemById(@PathVariable Long itemId){
        return redisService.deleteItemById(itemId);
    }

}
