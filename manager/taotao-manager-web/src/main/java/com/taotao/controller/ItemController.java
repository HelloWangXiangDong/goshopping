package com.taotao.controller;

import com.taotao.common.pojo.Page;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by XDStation on 2016/7/16 0016.
 * 商品管理service
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    //根据ID查询
    @RequestMapping(value="/item/{itemId}")     //映射路径
    public @ResponseBody TbItem getItemById(@PathVariable Long itemId){
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }
    @RequestMapping(value="/item/save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult saveItem(TbItem item,String desc,String itemParams) throws Exception {
        TaotaoResult result = itemService.saveItem(item,desc,itemParams);
        return result;
    }
    /**
     * 分页查询商品列表
     */
    @RequestMapping(value="/item/list", method=RequestMethod.POST)
    public @ResponseBody Page getItemList(Integer page,Integer rows){
        //调用itemService
        Page p = itemService.getItemList(page,rows);
        return p;
    }

    /**
     * 删除商品
     * @param ids
     * @return
     */
    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public TaotaoResult deleteItem(String ids){
        return itemService.deleteItem(ids);
    }

    @RequestMapping("item/desc/{itemId}")
    @ResponseBody
    public TaotaoResult getDesc(@PathVariable Long itemId){
        TaotaoResult desc = itemService.getDesc(itemId);
        return desc;
    }

    @RequestMapping("/item/update")
    @ResponseBody
    public TaotaoResult updateItem(TbItem item,String desc){
        TaotaoResult taotaoResult = itemService.updateItem(item, desc, "");
        return taotaoResult;
    }
}
