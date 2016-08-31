package com.taotao.controller;

import com.taotao.common.pojo.Page;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * Created by XDStation on 2016/7/26 0026.
 */
@Controller
@RequestMapping("item/param/")
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;

    /**
     * 根据ID查询商品分类列表，用于展示商品分类tree
     * @param itemCatId
     * @return
     */
    @RequestMapping("query/itemcatid/{itemCatId}")
    @ResponseBody
    public TaotaoResult getItemParamByid(@PathVariable Long itemCatId){
        TaotaoResult taotaoResult = itemParamService.getItemParamById(itemCatId);
        return taotaoResult;
    }

    /**
     * 查询所有分类信息
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Page getItemParam(int page, int rows){
        Page p = itemParamService.geItemParam(page,rows);
        return p;
    }
    @RequestMapping("save/{cid}")
    @ResponseBody
    public TaotaoResult saveItemParam(@PathVariable Long cid,String paramData){
        TbItemParam itemParam = new TbItemParam();
        itemParam.setParamData(paramData);
        itemParam.setItemCatId(cid);
        TaotaoResult result = itemParamService.saveItemParam(itemParam);
        return result;
    }
    @RequestMapping("delete")
    @ResponseBody
    public TaotaoResult deleteItem(String ids){
        return itemParamService.deleteItemParam(ids);
    }

}
