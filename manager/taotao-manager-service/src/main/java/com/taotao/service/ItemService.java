package com.taotao.service;

import com.taotao.common.pojo.Page;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

/**
 * Created by XDStation on 2016/7/16 0016.
 * 商品管理service
 */
public interface ItemService {
    //根据ID查询商品信息
    TbItem getItemById(long itemId);
    //分页返回所有商品列表
    Page getItemList(int page,int rows);
    //存商品
    TaotaoResult saveItem(TbItem item,String desc,String itemParams) throws Exception;
    //删除商品
    TaotaoResult deleteItem(String ids);
    //查询商品详情
    TaotaoResult getDesc(Long itemId);
    //更新商品信息
    TaotaoResult updateItem(TbItem item,String desc,String itemParams);
}
