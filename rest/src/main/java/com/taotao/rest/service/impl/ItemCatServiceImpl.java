package com.taotao.rest.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.dao.JedisDao;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDStation on 2016/8/1 0001.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService{
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Autowired
    private JedisDao jedisDao;
    @Value("#{configProperties['Index_ItemCat_Redis_Key']}")
    private String Index_ItemCat_Redis_Key;
    /**
     * 返回一个CatResult
     * @return
     */
    @Override
    public CatResult getItemCatList() {

        //从缓存中取数据
        try {
            String get = jedisDao.get(Index_ItemCat_Redis_Key);
            if(!StringUtils.isEmpty(get)){
                CatResult catResult = JsonUtils.jsonToPojo(get,CatResult.class);
                return catResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.getStackTrace(e);
        }

        CatResult catResult = new CatResult();
        catResult.setData(getCatData(0L));

        //向缓存中写入数据
        try {
            String s = JsonUtils.objectToJson(catResult);
            System.out.println("------------------"+s);
            jedisDao.set(Index_ItemCat_Redis_Key,s);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.getStackTrace(e);
        }

        return catResult;
    }

    /**
     * 查询分类列表
     * @param parentId
     * @return
     */
    //先来一个resultList存结果数据
    private List getCatData(Long parentId){
        //拼装查询结果给resultList
        List resultList = new ArrayList();
        //执行查询
        TbItemCatExample example = new TbItemCatExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        List<TbItemCat> itemCats = itemCatMapper.selectByExample(example);
        //前台只能来14个分类，控制一下
        int count = 0;
        for(TbItemCat itemCat : itemCats){
            //判断否为叶子节点
            if(itemCat.getIsParent()) {
                CatNode catNode = new CatNode();
                if (parentId == 0) {
                    catNode.setName("<a href='/products/" + itemCat.getId() + ".html'>" + itemCat.getName() + "</a>");
                } else {
                    catNode.setName(itemCat.getName());
                }
                catNode.setUrl("/products/" + itemCat.getId() + ".html");
                catNode.setItem(getCatData(itemCat.getId()));
                resultList.add(catNode);
                count ++;
                if(parentId == 0 && count >= 14){
                    break;
                }
            }else{
                resultList.add( "/products/"+itemCat.getId()+".html|"+itemCat.getName());
            }
        }
        return resultList;
    }
}
