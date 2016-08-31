package com.taotao.portal.service;

import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;

/**
 * Created by XDStation on 2016/8/9 0009.
 */
public interface ItemService {
    ItemInfo getItemById(Long id);
    TbItemDesc getItemDesc(Long itemId);
    String getItemParam(Long itemId);
}
