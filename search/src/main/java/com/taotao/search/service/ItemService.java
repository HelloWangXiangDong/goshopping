package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.pojo.Item;

/**
 * Created by XDStation on 2016/8/8 0008.
 */
public interface ItemService {
    TaotaoResult importAllItems();
    TaotaoResult addItem(Item item);
}
