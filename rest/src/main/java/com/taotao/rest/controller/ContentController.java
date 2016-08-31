package com.taotao.rest.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbContent;
import com.taotao.rest.service.ContentServce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by XDStation on 2016/8/4 0004.
 */
@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentServce contentServce;
    @RequestMapping("/list/{contentCategoryId}")
    @ResponseBody
    public TaotaoResult getContent(@PathVariable Long contentCategoryId){
        try {
            List<TbContent> contents = contentServce.getContent(contentCategoryId);
            return TaotaoResult.ok(contents);
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

    }

}
