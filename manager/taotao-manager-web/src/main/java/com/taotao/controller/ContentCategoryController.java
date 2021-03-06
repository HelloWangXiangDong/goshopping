package com.taotao.controller;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by XDStation on 2016/8/3 0003.
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;
    @RequestMapping("/list")
    @ResponseBody
    public List<EUTreeNode> getContentCategory(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        return contentCategoryService.getContentCategory(parentId);
    }
    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult addContentCategory(Long parentId, String name){
        return contentCategoryService.insertContentCategory(parentId,name);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContentCategory(Long parentId,Long id){
        return contentCategoryService.deleteContentCategory(parentId,id);
    }
    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateContentCategory(Long id,String name){
        return contentCategoryService.updateContentCategory(id,name);
    }
}