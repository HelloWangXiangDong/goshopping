package com.taotao.rest.service;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;

import java.util.List;

/**
 * Created by XDStation on 2016/8/9 0009.
 */
public interface ItemService {
    TbItem getItemById(Long id);
    TbItemDesc getItemDesc(Long itemId);
    TbItemParamItem getItemParam(Long itemId);
}
