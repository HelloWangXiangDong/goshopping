package com.taotao.order.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.dao.JedisDao;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by XDStation on 2016/8/17 0017.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private JedisDao jedisDao;


    @Value("#{configProperties['OrderId_Generation_Key']}")
    private String OrderId_Generation_Key;
    @Value("#{configProperties['OrderId_InitId']}")
    private String OrderId_InitId;
    @Value("#{configProperties['Order_Detail']}")
    private String Order_Detail;

    @Override
    public TaotaoResult addOrder(TbOrder order, List<TbOrderItem> items, TbOrderShipping orderShipping) {
        //向订单表中插入数据
        //获得订单ID
        String s = jedisDao.get(OrderId_Generation_Key);
        if(StringUtils.isEmpty(s)){
            jedisDao.set(OrderId_Generation_Key,OrderId_InitId);
        }
        long orderId = jedisDao.incr(OrderId_Generation_Key);
        order.setOrderId(orderId+"");
        //设置状态，1未付款 2已付款 3未发货 4已发货 5交易成功 6交易关闭
        order.setStatus(1);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        //是否评价，1已评价 0未评价
        order.setBuyerRate(0);
        orderMapper.insert(order);
        for(TbOrderItem item : items){
            //生成明细ID
            long detail_id = jedisDao.incr(Order_Detail);
            item.setId(detail_id+"");
            item.setOrderId(orderId+"");
            //向订单明细表中插入数据
            orderItemMapper.insert(item);
        }
        //插入物流表
        //补全物流表信息
        orderShipping.setOrderId(orderId+"");
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShippingMapper.insert(orderShipping);
        return TaotaoResult.ok(orderId);
    }
}
