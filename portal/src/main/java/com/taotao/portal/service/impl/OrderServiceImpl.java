package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by XDStation on 2016/8/18 0018.
 */
@Service
public class OrderServiceImpl implements OrderService
{
    @Value("#{configProperties['Order_BaseUrl']}")
    private String Order_BaseUrl;
    @Value("#{configProperties['Order_Create']}")
    private String Order_Create;

    @Override
    public String createOrder(Order order) {
        //调用order的服务
        String s = HttpClientUtil.doPostJson(Order_BaseUrl + Order_Create, JsonUtils.objectToJson(order));
        TaotaoResult result = TaotaoResult.format(s);
        if(result.getStatus() == 200){
            return result.getData()+"";
        }
        return "";
    }
}
