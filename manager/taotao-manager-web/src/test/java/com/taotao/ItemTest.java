package com.taotao;

import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * Created by XDStation on 2016/7/26 0026.
 */
public class ItemTest {
    public static void main(String[] args) {
        //创建一个spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        //拿mapper代理对象
        TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
        //创建一个Item实例  146944970481908
        TbItemExample example = new TbItemExample();
        //创建条件
        example.createCriteria().andIdEqualTo(146944970481908L);
        //创建Item
        TbItem item = new TbItem();
        item.setTitle("惊艳全场");
        item.setSellPoint("非常漂亮！！");
        item.setId(146944970481908L);
        item.setUpdated(new Date());
        item.setCreated(new Date());
        item.setImage("25/1469449676711897.jpg");
        item.setPrice(23456L);
        item.setNum(1234);
        item.setStatus((byte)0);
        item.setCid(298L);
        System.out.println(itemMapper.updateByExample(item,example));
    }
}
