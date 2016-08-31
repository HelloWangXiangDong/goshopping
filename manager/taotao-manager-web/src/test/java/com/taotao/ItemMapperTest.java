package com.taotao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.myMapper.ItemParamMapper;
import com.taotao.pojo.TbItemParam;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Created by XDStation on 2016/7/27 0027.
 */
public class ItemMapperTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        //拿mapper代理对象
        ItemParamMapper itemParamMapper = applicationContext.getBean(ItemParamMapper.class);
        //分页处理
        PageHelper.startPage(1,5);
        List<TbItemParam> tbItems = new ArrayList<TbItemParam>();
        tbItems =  itemParamMapper.getItemMapper();
        for(TbItemParam param : tbItems){
            System.out.println(param.getItemCatName());
        }

        PageInfo pageInfo = new PageInfo(tbItems);
        System.out.println("总数据条数："+pageInfo.getTotal());
    }
}
