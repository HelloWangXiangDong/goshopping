package com.taotao.rest.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.rest.dao.JedisDao;
import com.taotao.rest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by XDStation on 2016/8/7 0007.
 */
@Service
public class RedisServiceImpl implements RedisService {
    
    @Autowired
    private JedisDao jedisDao;

    @Value("#{configProperties['Index_Content_Redis_Key']}")
    private String Index_Content_Redis_Key;

    @Value("#{configProperties['Index_ItemCat_Redis_Key']}")
    private String Index_ItemCat_Redis_Key;
    @Value("#{configProperties['Item_Base_KEY']}")
    private String Item_Base_KEY;

    @Override
    public TaotaoResult deleteADcache(long contentCid) {
        try {
            jedisDao.hdel(Index_Content_Redis_Key, contentCid+"");
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteItemCatCache() {
        try {
            jedisDao.del(Index_ItemCat_Redis_Key);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteItemById(Long id) {
        jedisDao.del(Item_Base_KEY + ":" + id + ":desc");
        jedisDao.del(Item_Base_KEY + ":" + id + ":base");
        return TaotaoResult.ok();
    }
}
