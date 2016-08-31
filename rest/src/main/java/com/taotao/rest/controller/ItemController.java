package com.taotao.rest.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by XDStation on 2016/8/9 0009.
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 商品基本信息
     * @param id
     * @return
     */
    @RequestMapping("/item/getItem/{id}")
    @ResponseBody
    public TaotaoResult getItemById(@PathVariable Long id){
        TbItem item = itemService.getItemById(id);
        return TaotaoResult.ok(item);
    }

    /**
     * 商品详情
     * @param itemId
     * @return
     */
    @RequestMapping("/item/getItemDesc/{itemId}")
    @ResponseBody
    public TaotaoResult getItemDesc(@PathVariable Long itemId){
        TbItemDesc item = itemService.getItemDesc(itemId);
        return TaotaoResult.ok(item);
    }

    @RequestMapping("/item/getItemParam/{itemId}")
    @ResponseBody
    public TaotaoResult getItemParam(@PathVariable Long itemId){
        TbItemParamItem itemParam = itemService.getItemParam(itemId);
        return TaotaoResult.ok(itemParam);
    }

}
