package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * Created by XDStation on 2016/8/7 0007.
 */
public interface RedisService {
    //清除首页大广告缓存
    TaotaoResult deleteADcache(long cotentCid);
    //清除分类列表缓存
    TaotaoResult deleteItemCatCache();
    //清除商品缓存
    TaotaoResult deleteItemById(Long id);
}
