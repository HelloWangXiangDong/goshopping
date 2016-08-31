package com.taotao.order.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

import java.util.List;

/**
 * Created by XDStation on 2016/8/17 0017.
 */
public interface OrderService {
    TaotaoResult addOrder(TbOrder order, List<TbOrderItem> items, TbOrderShipping orderShipping);
}
