package com.taotao.search.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.pojo.Item;
import com.taotao.search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/manager")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 导入商品数据到索引库
     */
    @RequestMapping("/importall")
    @ResponseBody
    public TaotaoResult importAllItems() {
        TaotaoResult result = itemService.importAllItems();
        return result;
    }
    /**
     * 导入商品数据到索引库
     */
    @RequestMapping("/importItem")
    @ResponseBody
    public TaotaoResult importItem(Item item) {
        try {
            item.setTitle(new String(item.getTitle().getBytes("iso8859-1"),"utf-8"));
            item.setCategory_name(new String(item.getCategory_name().getBytes("iso8859-1"),"utf-8"));
            item.setImage(new String(item.getImage().getBytes("iso8859-1"),"utf-8"));
            item.setItem_des(new String(item.getItem_des().getBytes("iso8859-1"),"utf-8"));
            item.setId(new String(item.getId().getBytes("iso8859-1"),"utf-8"));
            item.setSell_point(new String(item.getSell_point().getBytes("iso8859-1"),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        TaotaoResult result = itemService.addItem(item);
        return result;
    }
}
