package com.taotao.rest.service.impl;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.rest.dao.JedisDao;
import com.taotao.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by XDStation on 2016/8/9 0009.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Autowired
    private JedisDao jedisDao;

    @Value("#{configProperties['Item_Base_KEY']}")
    private String Item_Base_KEY;

    @Value("#{configProperties['Item_BaseKey_Expire']}")
    private Integer Item_BaseKey_Expire;

    @Override
    public TbItem getItemById(Long id) {
        try {
            //先从redis中读数据
            String s = jedisDao.get(Item_Base_KEY + ":" + id + ":base");
            if(!StringUtils.isEmpty(s)){
                TbItem item_ = JsonUtils.jsonToPojo(s, TbItem.class);
                return item_;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbItem item = itemMapper.selectByPrimaryKey(id);

        try {
            //向redis中写入数据
            jedisDao.set(Item_Base_KEY+":"+id+":base", JsonUtils.objectToJson(item));
            //设置key的有效期
            jedisDao.expire(Item_Base_KEY+":"+id+":base",Item_BaseKey_Expire);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public TbItemDesc getItemDesc(Long itemId) {
        try {
            //读取缓存
            String s = jedisDao.get(Item_Base_KEY + ":" + itemId + ":desc");
            if(!StringUtils.isEmpty(s)){
                TbItemDesc itemDesc_ = JsonUtils.jsonToPojo(s, TbItemDesc.class);
                return itemDesc_;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

        try {
            //设置缓存
            jedisDao.set(Item_Base_KEY+":"+itemId+":desc",JsonUtils.objectToJson(itemDesc));
            jedisDao.expire(Item_Base_KEY+":"+itemId+":desc",Item_BaseKey_Expire);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemDesc;
    }

    @Override
    public TbItemParamItem getItemParam(Long itemId) {
        try {
            //取缓存
            String s = jedisDao.get(Item_Base_KEY + ":" + itemId + ":param");
            if(!StringUtils.isEmpty(s)) {
                TbItemParamItem itemParamItem_ = JsonUtils.jsonToPojo(s, TbItemParamItem.class);
                return itemParamItem_;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbItemParamItemExample example = new TbItemParamItemExample();
        example.createCriteria().andItemIdEqualTo(itemId);
        List<TbItemParamItem> paramItems = itemParamItemMapper.selectByExampleWithBLOBs(example);
        TbItemParamItem itemParamItem = null;
        if(paramItems.size()>0 && paramItems!=null){
            itemParamItem = paramItems.get(0);
        }

        try {
            //设置缓存
            jedisDao.set(Item_Base_KEY+":"+itemId+":param",JsonUtils.objectToJson(itemParamItem));
            jedisDao.expire(Item_Base_KEY+":"+itemId+":param",Item_BaseKey_Expire);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemParamItem;
    }
}
