package com.taotao.controller;

import com.taotao.common.pojo.Page;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by XDStation on 2016/8/3 0003.
 */
@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService;
    @RequestMapping("/query/list")
    @ResponseBody
    public Page getContentQuery(Long categoryId,int page,int rows){
        return contentService.getContentCategoryQuery(categoryId,page,rows);
    }
    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult saveContent(TbContent content){
        return contentService.insertContentCategory(content);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContent(String ids){
        return contentService.deleteContent(ids);
    }
    @RequestMapping("/edit")
    @ResponseBody
    public TaotaoResult updateContent(TbContent content){
        return contentService.updateContent(content);
    }
}
