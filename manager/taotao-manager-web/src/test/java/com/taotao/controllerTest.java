package com.taotao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by XDStation on 2016/7/16 0016.
 */
public class controllerTest {
    @Test
    public void testPage() {
        /*//创建一个spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        //从spring容器中获得mapper代理对象
        TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
        //创建一个Item实例
        TbItemExample example = new TbItemExample();
        //分页处理
        PageHelper.startPage(3,10);
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        //取商品列表
        for(TbItem tb : tbItems){
            System.out.println(tb.getTitle());
        }
        System.out.println("---------------");
        //取分页信息
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(tbItems);
        Long total = pageInfo.getTotal();
        System.out.println("共有商品："+total+"件。");*/
        //创建一个spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        //拿mapper代理对象
        TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
        //创建一个Item实例
        TbItemExample example = new TbItemExample();
        //分页处理
        PageHelper.startPage(1,10);
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        for (TbItem t : tbItems){
            System.out.println(t.getTitle());
        }
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(tbItems);
        System.out.println("总数据条数："+pageInfo.getTotal());
    }
}
